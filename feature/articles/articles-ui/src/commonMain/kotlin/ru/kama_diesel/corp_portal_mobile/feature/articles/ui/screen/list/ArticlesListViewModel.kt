package ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list

import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.ui.base.BaseStateViewModel
import ru.kama_diesel.corp_portal_mobile.feature.articles.domain.di.ArticlesListScope
import ru.kama_diesel.corp_portal_mobile.feature.articles.domain.usecase.*
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.model.ArticlesListDialog
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.model.ArticlesListViewState
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.model.CommentSendingState
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.model.TagItemUIModel

@ArticlesListScope
@Inject
class ArticlesListViewModel(
    private val getArticlesListUseCase: GetArticlesListUseCase,
    private val getTagsUseCase: GetTagsUseCase,
    private val getArticleDetailsUseCase: GetArticleDetailsUseCase,
    private val sendCommentUseCase: SendCommentUseCase,
    private val likeUseCase: LikeUseCase,
    private val initialState: ArticlesListViewState,
) : BaseStateViewModel<ArticlesListViewState>() {

    init {
        getData()
    }

    fun getData() {
        coroutineScope.launch {
            val articleItems = getArticlesListUseCase(
                fromDate = currentState.fromDate,
                toDate = currentState.toDate,
                selectedTagsIds = currentState.tagItems.filter { it.isChecked }.map { it.tagItem.id },
            )
            val tagItems = getTagsUseCase()
            setState {
                copy(
                    articleItems = articleItems,
                    tagItems = tagItems.map { tagItem ->
                        TagItemUIModel(
                            tagItem = tagItem,
                            isChecked = currentState.tagItems.firstOrNull { it.tagItem.id == tagItem.id }?.isChecked
                                ?: false
                        )
                    },
                    isLoading = false,
                    dialog = ArticlesListDialog.No,
                )
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
        getData()
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
                val isSendCommentSuccess = sendCommentUseCase(postId = articleId, comment = comment.trim())
                if (isSendCommentSuccess) {
                    setState {
                        copy(
                            dialog = copy(
                                comment = "",
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
            val articleDetailsItem = getArticleDetailsUseCase(articleId = articleId)
            setState {
                copy(
                    dialog = ArticlesListDialog.Details(
                        articleId = articleId,
                        title = title,
                        imagePaths = List(4) { imagePaths!! }.flatten(),
                        tags = tags,
                        creationDate = creationDate,
                        isLiked = isLiked,
                        likesAmount = likesAmount,
                        articleDetailsItem = articleDetailsItem,
                        comment = "",
                        commentSendingState = CommentSendingState.No,
                    ),
                )
            }
        }
    }

    override fun createInitialState() = initialState
}
