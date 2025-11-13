package ru.kama_diesel.corp_portal_mobile.feature.articles.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.data.network.api.CorpPortalApi
import ru.kama_diesel.corp_portal_mobile.common.data.network.mapper.DateTimeMapper
import ru.kama_diesel.corp_portal_mobile.common.data.network.model.LikeRequestData
import ru.kama_diesel.corp_portal_mobile.common.data.network.model.SendCommentRequestData
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IArticlesRepository
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ArticleDetailsItem
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ArticleItem
import ru.kama_diesel.corp_portal_mobile.common.domain.model.CommentItem
import ru.kama_diesel.corp_portal_mobile.common.domain.model.TagItem

@Inject
class ArticlesRepository(
    private val corpPortalApi: CorpPortalApi,
) : IArticlesRepository {

    override suspend fun getArticlesList(
        fromDate: Long?,
        toDate: Long?,
        selectedTagsIds: List<String>
    ): List<ArticleItem> {
        return withContext(Dispatchers.IO) {
            corpPortalApi.getArticles(
                fromDate = fromDate,
                toDate = toDate,
                selectedTagsIds = selectedTagsIds,
            ).articles?.map {
                ArticleItem(
                    id = it.postId,
                    title = it.title,
                    text = it.text,
                    creationDate = DateTimeMapper.getFormattedDate(
                        iso8601Timestamp = it.creationDate,
                        format = "dd.MM.yyyy HH:mm",
                    ),
                    imagePaths = it.imagesPaths,
                    tags = it.tags?.map { tag -> tag.name },
                    isLiked = it.isLiked,
                    likesAmount = it.likesAmount,
                )
            } ?: listOf()
        }
    }

    override suspend fun getTagsList(): List<TagItem> {
        return withContext(Dispatchers.IO) {
            corpPortalApi.getTags().tags?.map {
                TagItem(
                    id = it.tagId,
                    name = it.name,
                    textColor = it.textColor,
                    backgroundColor = it.backgroundColor,
                )
            } ?: listOf()
        }
    }

    override suspend fun getArticleDetails(articleId: String): ArticleDetailsItem {
        return withContext(Dispatchers.IO) {
            corpPortalApi.getArticleDetails(articleId = articleId).article.let {
                ArticleDetailsItem(
                    text = it.text,
                    comments = it.comments?.map { comment ->
                        CommentItem(
                            commentId = comment.commentId,
                            userId = comment.userId,
                            text = comment.text,
                            creationDate = DateTimeMapper.getFormattedDate(
                                iso8601Timestamp = comment.creationDate,
                                format = "dd.MM.yyyy HH:mm",
                            ),
                            fullName = comment.fullName,
                            position = comment.position,
                            department = comment.department,
                            imagePath = comment.imagePath,
                        )
                    }
                )
            }
        }
    }

    override suspend fun sendComment(postId: String, comment: String) {
        withContext(Dispatchers.IO) {
            corpPortalApi.sendComment(
                sendCommentRequestData = SendCommentRequestData(
                    postId = postId.toInt(),
                    text = comment,
                )
            )
        }
    }

    override suspend fun like(postId: String) {
        withContext(Dispatchers.IO) {
            corpPortalApi.like(
                likeRequestData = LikeRequestData(
                    postId = postId.toInt(),
                )
            )
        }
    }
}
