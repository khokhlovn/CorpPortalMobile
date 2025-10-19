package ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.model

import kotlinx.serialization.Serializable
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ArticleItem
import ru.kama_diesel.corp_portal_mobile.common.domain.model.TagItem

@Serializable
data class ArticlesListViewState(
    val articleItems: List<ArticleItem>,
    val tagItems: List<TagItemUIModel>,
    val isLoading: Boolean,
)

@Serializable
data class TagItemUIModel(
    val isChecked: Boolean,
    val tagItem: TagItem,
)
