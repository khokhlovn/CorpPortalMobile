package ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.model

import kotlinx.serialization.Serializable
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ArticleDetailsItem
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ArticleItem
import ru.kama_diesel.corp_portal_mobile.common.domain.model.TagItem

@Serializable
data class ArticlesListViewState(
    val articleItems: List<ArticleItem>,
    val dialog: ArticlesListDialog,
    val tagItems: List<TagItemUIModel>,
    val fromDate: Long?,
    val toDate: Long?,
    val isLoading: Boolean,
    val openedImagesPaths: List<String>,
    val selectedImageIndex: Int,
)

@Serializable
sealed class ArticlesListDialog {
    @Serializable
    data object No : ArticlesListDialog()

    @Serializable
    data object Loading : ArticlesListDialog()

    @Serializable
    data class Details(
        val articleId: String,
        val title: String,
        val imagePaths: List<String>?,
        val tags: List<String>?,
        val creationDate: String,
        val isLiked: Boolean,
        val likesAmount: Int,
        val articleDetailsItem: ArticleDetailsItem,
        val comment: String,
        val commentSendingState: CommentSendingState,
    ) : ArticlesListDialog()
}

@Serializable
sealed class CommentSendingState {
    @Serializable
    data object No : CommentSendingState()

    @Serializable
    data object Sendind : CommentSendingState()

    @Serializable
    data object Success : CommentSendingState()
}

@Serializable
data class TagItemUIModel(
    val isChecked: Boolean,
    val tagItem: TagItem,
)
