package ru.kama_diesel.corp_portal_mobile.common.domain.interfaces

import ru.kama_diesel.corp_portal_mobile.common.domain.model.ArticleDetailsItem
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ArticleItem
import ru.kama_diesel.corp_portal_mobile.common.domain.model.TagItem

interface IArticlesRepository {
    suspend fun getArticlesList(page: Int, fromDate: Long?, toDate: Long?, selectedTagsIds: List<String>): List<ArticleItem>

    suspend fun getTagsList(): List<TagItem>

    suspend fun getArticleDetails(articleId: String, userId: String): ArticleDetailsItem

    suspend fun sendComment(postId: String, comment: String, replyTo: Int?)

    suspend fun like(postId: String)

    suspend fun commentLike(commentId: String)

    suspend fun getMyUserId(): Int
}
