package ru.kama_diesel.corp_portal_mobile.common.domain.interfaces

import ru.kama_diesel.corp_portal_mobile.common.domain.model.ArticleItem

interface IArticlesRepository {
    suspend fun observeArticlesList(): List<ArticleItem>
}
