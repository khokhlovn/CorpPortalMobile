package ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.model

import kotlinx.serialization.Serializable
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ArticleItem

@Serializable
data class ArticlesListViewState(
    val articleItems: List<ArticleItem>,
)
