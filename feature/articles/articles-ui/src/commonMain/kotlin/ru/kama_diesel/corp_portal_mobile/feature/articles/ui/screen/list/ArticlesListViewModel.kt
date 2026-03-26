package ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list

import io.github.ahmad_hamwi.compose.pagination.PaginationState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ArticleItem
import ru.kama_diesel.corp_portal_mobile.common.domain.model.CommentItem
import ru.kama_diesel.corp_portal_mobile.common.ui.base.BaseStateViewModel
import ru.kama_diesel.corp_portal_mobile.feature.articles.domain.di.ArticlesListScope
import ru.kama_diesel.corp_portal_mobile.feature.articles.domain.usecase.CommentLikeUseCase
import ru.kama_diesel.corp_portal_mobile.feature.articles.domain.usecase.GetArticleDetailsUseCase
import ru.kama_diesel.corp_portal_mobile.feature.articles.domain.usecase.GetArticlesListUseCase
import ru.kama_diesel.corp_portal_mobile.feature.articles.domain.usecase.GetMyUserIdUseCase
import ru.kama_diesel.corp_portal_mobile.feature.articles.domain.usecase.GetTagsUseCase
import ru.kama_diesel.corp_portal_mobile.feature.articles.domain.usecase.LikeUseCase
import ru.kama_diesel.corp_portal_mobile.feature.articles.domain.usecase.SendCommentUseCase
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.model.ArticleDetailsUIModel
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.model.ArticlesListDialog
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.model.ArticlesListViewState
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.model.CommentSendingState
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.model.CommentUIModel
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.model.TagItemUIModel

@ArticlesListScope
@Inject
class ArticlesListViewModel(
    private val getArticlesListUseCase: GetArticlesListUseCase,
    private val getTagsUseCase: GetTagsUseCase,
    private val getArticleDetailsUseCase: GetArticleDetailsUseCase,
    private val sendCommentUseCase: SendCommentUseCase,
    private val likeUseCase: LikeUseCase,
    private val commentLikeUseCase: CommentLikeUseCase,
    private val getMyUserIdUseCase: GetMyUserIdUseCase,
    private val initialState: ArticlesListViewState,
) : BaseStateViewModel<ArticlesListViewState>() {

    lateinit var job: Job

    val paginationState = PaginationState<Int, ArticleItem>(
        initialPageKey = 1,
        onRequestPage = { pageNumber ->
            if (::job.isInitialized) {
                job.cancel()
            }
            job = coroutineScope.launch {
                try {
                    val page = onRequestPage(pageNumber)
                    appendPage(
                        items = page.first,
                        nextPageKey = page.second,
                    )
                } catch (e: Exception) {
                    setError(e)
                    appendPage(
                        items = listOf(),
                        nextPageKey = pageNumber + 1,
                        isLastPage = true,
                    )
                }
            }
        }
    )

    init {
        getData(needListRefresh = false)
    }

    fun getData(needListRefresh: Boolean) {
        coroutineScope.launch {
            val tagItems = getTagsUseCase()
            setState {
                copy(
                    tagItems = tagItems.map { tagItem ->
                        TagItemUIModel(
                            tagItem = tagItem,
                            isChecked = currentState.tagItems.firstOrNull { it.tagItem.id == tagItem.id }?.isChecked
                                ?: false
                        )
                    },
                    isLoading = needListRefresh,
                    dialog = ArticlesListDialog.No,
                )
            }
            if (needListRefresh) {
                paginationState.refresh(initialPageKey = 1)
            }
        }
    }

    fun checkTag(id: String, isChecked: Boolean) {
        setState {
            copy(
                tagItems = tagItems.map { tagItemUIModel ->
                    tagItemUIModel.copy(
                        isChecked = if (tagItemUIModel.tagItem.id == id) isChecked else tagItemUIModel.isChecked
                    )
                }
            )
        }
    }

    fun onDateChange(fromDate: Long?, toDate: Long?) {
        setState {
            copy(
                fromDate = fromDate,
                toDate = toDate,
            )
        }
    }

    fun onResetFilters() {
        setState {
            copy(
                fromDate = null,
                toDate = null,
                tagItems = tagItems.map { tagItemUIModel ->
                    tagItemUIModel.copy(
                        isChecked = false,
                    )
                },
                dialog = ArticlesListDialog.Loading,
            )
        }
        getData(needListRefresh = true)
    }

    fun onArticleClick(
        articleId: String,
        title: String,
        imagePaths: List<String>?,
        tags: List<String>?,
        creationDate: String,
        isLiked: Boolean,
        likesAmount: Int,
    ) {
        setState {
            copy(dialog = ArticlesListDialog.Loading)
        }

        loadArticleDetails(
            articleId = articleId,
            title = title,
            imagePaths = imagePaths,
            tags = tags,
            creationDate = creationDate,
            isLiked = isLiked,
            likesAmount = likesAmount,
        )
    }

    fun onCloseDetailsClick() {
        setState {
            copy(
                dialog = ArticlesListDialog.No,
            )
        }
    }

    fun onCommentChange(comment: String) {
        setState {
            copy(
                dialog = (dialog as? ArticlesListDialog.Details)?.copy(comment = comment) ?: dialog,
            )
        }
    }

    fun onSendComment() {
        with(currentState.dialog as? ArticlesListDialog.Details ?: return) {
            setState {
                copy(
                    dialog = copy(
                        commentSendingState = CommentSendingState.Sending,
                    ),
                )
            }
            coroutineScope.launch {
                val isSendCommentSuccess = sendCommentUseCase(postId = articleId, comment = comment.trim(), replyTo = replyTo)
                if (isSendCommentSuccess) {
                    setState {
                        copy(
                            dialog = copy(
                                comment = "",
                                replyTo = null,
                                commentSendingState = CommentSendingState.Success,
                            ),
                        )
                    }
                } else {
                    setState {
                        copy(
                            dialog = copy(
                                commentSendingState = CommentSendingState.No,
                            ),
                        )
                    }
                }
            }
        }
    }

    fun onHideCommentSentSnackbar() {
        with(currentState.dialog as? ArticlesListDialog.Details ?: return) {
            setState {
                copy(
                    dialog = copy(
                        commentSendingState = CommentSendingState.No,
                    ),
                )
            }
        }
    }

    fun onLikeClick() {
        with(currentState.dialog as? ArticlesListDialog.Details ?: return) {
            coroutineScope.launch {
                val isLikeSuccess = likeUseCase(postId = articleId)
                if (isLikeSuccess) {
                    setState {
                        copy(
                            dialog = copy(
                                isLiked = true,
                                likesAmount = likesAmount + 1,
                            ),
                        )
                    }
                }
            }
        }
    }

    fun onCommentLikeClick(commentId: String) {
        with(currentState.dialog as? ArticlesListDialog.Details ?: return) {
            coroutineScope.launch {
                val isLikeSuccess = commentLikeUseCase(commentId = commentId)
                if (isLikeSuccess) {
                    setState {
                        copy(
                            dialog = copy(
                                articleDetailsItem = articleDetailsItem.copy(
                                    originalComments = articleDetailsItem.originalComments.map {
                                        if (it.commentId.toString() != commentId) {
                                            it
                                        } else {
                                            it.copy(
                                                isLiked = true,
                                                likesAmount = it.likesAmount + 1,
                                            )
                                        }
                                    },
                                    comments = articleDetailsItem.comments.mapKeys {
                                        if (it.key.commentId.toString() == commentId) {
                                            it.key.copy(
                                                isLiked = true,
                                                likesAmount = it.key.likesAmount + 1,
                                            )
                                        } else {
                                            it.key
                                        }
                                    }.mapValues {
                                        it.value.map { commentItem ->
                                            if (commentItem.commentId.toString() != commentId) {
                                                commentItem
                                            } else {
                                                commentItem.copy(
                                                    isLiked = true,
                                                    likesAmount = commentItem.likesAmount + 1,
                                                )
                                            }
                                        }
                                    }
                                )
                            ),
                        )
                    }
                }
            }
        }
    }

    fun onChangeRepliesVisibility(commentId: Int) {
        with(currentState.dialog as? ArticlesListDialog.Details ?: return) {
            setState {
                copy(
                    dialog = copy(
                        articleDetailsItem = articleDetailsItem.copy(
                            comments = articleDetailsItem.comments.mapKeys {
                                if (it.key.commentId == commentId) {
                                    it.key.copy(
                                        isExpanded = !it.key.isExpanded,
                                    )
                                } else {
                                    it.key
                                }
                            }
                        )
                    ),
                )
            }
        }
    }

    fun onReplyClick(commentId: Int) {
        with(currentState.dialog as? ArticlesListDialog.Details ?: return) {
            setState {
                copy(
                    dialog = copy(
                        replyTo = commentId,
                        comment = articleDetailsItem.originalComments.find { it.commentId == commentId }?.fullName?.split(" ")[1]?.let {
                            "$it, "
                        } ?: ""
                    ),
                )
            }
        }
    }

    fun onCancelReplyClick() {
        with(currentState.dialog as? ArticlesListDialog.Details ?: return) {
            setState {
                copy(
                    dialog = copy(
                        replyTo = null,
                    ),
                )
            }
        }
    }

    suspend fun onRequestPage(page: Int): Pair<List<ArticleItem>, Int> {
        if (page == 1) {
            setState {
                copy(
                    isLoading = true,
                )
            }
        }
        val pageWithData = getArticlesListUseCase(
            page = page,
            fromDate = currentState.fromDate,
            toDate = currentState.toDate,
            selectedTagsIds = currentState.tagItems.filter { it.isChecked }.map { it.tagItem.id },
        ) to page + 1
        if (page == 1) {
            setState {
                copy(
                    isLoading = false,
                )
            }
        }
        return pageWithData
    }

    private fun loadArticleDetails(
        articleId: String,
        title: String,
        imagePaths: List<String>?,
        tags: List<String>?,
        creationDate: String,
        isLiked: Boolean,
        likesAmount: Int,
    ) {
        coroutineScope.launch {
            val myUserId = getMyUserIdUseCase()
            val articleDetailsItem = getArticleDetailsUseCase(articleId = articleId, userId = myUserId.toString())
            setState {
                copy(
                    dialog = ArticlesListDialog.Details(
                        articleId = articleId,
                        title = title,
                        imagePaths = imagePaths?.let { List(4) { imagePaths }.flatten() },
                        tags = tags,
                        creationDate = creationDate,
                        isLiked = isLiked,
                        likesAmount = likesAmount,
                        articleDetailsItem = with(articleDetailsItem) {
                            val commentsWithReplies = mutableMapOf<CommentUIModel, List<CommentItem>>()
                            comments.sortedBy {
                                val dateTimeFormatter = LocalDateTime.Format {
                                    @OptIn(FormatStringsInDatetimeFormats::class)
                                    byUnicodePattern("dd.MM.yyyy HH:mm")
                                }
                                LocalDateTime.parse(it.creationDate, dateTimeFormatter)
                            }.forEach { comment ->
                                if (comment.replyTo == null) {
                                    val rootComment = CommentUIModel(
                                        commentId = comment.commentId,
                                        userId = comment.userId,
                                        text = comment.text,
                                        creationDate = comment.creationDate,
                                        fullName = comment.fullName,
                                        position = comment.position,
                                        department = comment.department,
                                        imagePath = comment.imagePath,
                                        likesAmount = comment.likesAmount,
                                        isLiked = comment.isLiked,
                                        isExpanded = false,
                                    )
                                    commentsWithReplies[rootComment] = listOf()
                                } else {
                                    commentsWithReplies.keys.firstOrNull { existingComment ->
                                        existingComment.commentId == comment.replyTo
                                                || commentsWithReplies[existingComment]?.any { it.commentId == comment.replyTo } ?: false
                                    }?.let {
                                        commentsWithReplies[it] =
                                            commentsWithReplies[it]?.plus(comment) ?: listOf(comment)
                                    }
                                }
                            }
                            ArticleDetailsUIModel(
                                text = text,
                                originalComments = comments,
                                comments = commentsWithReplies,
                            )
                        },
                        comment = "",
                        replyTo = null,
                        myUserId = myUserId,
                        commentSendingState = CommentSendingState.No,
                    ),
                )
            }
        }
    }

    override fun createInitialState() = initialState
}
