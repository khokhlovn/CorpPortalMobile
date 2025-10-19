package ru.kama_diesel.corp_portal_mobile.common.domain.interfaces

import ru.kama_diesel.corp_portal_mobile.common.domain.model.ArticleItem
import ru.kama_diesel.corp_portal_mobile.common.domain.model.TagItem

interface IArticlesRepository {
    suspend fun getArticlesList(): List<ArticleItem>

    suspend fun getTagsList(): List<TagItem>
}
