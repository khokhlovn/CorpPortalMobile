package ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list

import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.ui.base.BaseStateViewModel
import ru.kama_diesel.corp_portal_mobile.common.ui.navigation.RouterHolder
import ru.kama_diesel.corp_portal_mobile.feature.articles.domain.api.ILogoutUseCase
import ru.kama_diesel.corp_portal_mobile.feature.articles.domain.di.ArticlesListScope
import ru.kama_diesel.corp_portal_mobile.feature.articles.domain.usecase.GetArticleDetailsUseCase
import ru.kama_diesel.corp_portal_mobile.feature.articles.domain.usecase.GetArticlesListUseCase
import ru.kama_diesel.corp_portal_mobile.feature.articles.domain.usecase.GetTagsUseCase
import ru.kama_diesel.corp_portal_mobile.feature.articles.domain.usecase.SendCommentUseCase
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.api.IArticlesFlowRouter
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
    routerHolder: RouterHolder<IArticlesFlowRouter>,
    private val logout: ILogoutUseCase,
    private val initialState: ArticlesListViewState,
) : BaseStateViewModel<ArticlesListViewState>() {

    private val router by routerHolder

    init {
        getData()
    }

    fun onLogoutClick() {
        logout()
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
                        commentSendingState = CommentSendingState.Sendind,
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

    private fun loadArticleDetails(
        articleId: String,
        title: String,
        imagePaths: List<String>?,
        tags: List<String>?,
        creationDate: String,
    ) {
        coroutineScope.launch {
            val articleDetailsItem = getArticleDetailsUseCase(articleId = articleId)
            setState {
                copy(
                    dialog = ArticlesListDialog.Details(
                        articleId = articleId,
                        title = title,
                        imagePaths = imagePaths,
                        tags = tags,
                        creationDate = creationDate,
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
