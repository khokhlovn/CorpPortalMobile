package ru.kama_diesel.corp_portal_mobile.feature.articles.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.data.network.api.CorpPortalApi
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IArticlesRepository
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ArticleItem
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
            ).articles.map {
                ArticleItem(
                    id = it.postId,
                    title = it.title,
                    text = it.text,
                    imagePath = it.imagesPaths?.firstOrNull(),
                )
            }
        }
    }

    override suspend fun getTagsList(): List<TagItem> {
        return withContext(Dispatchers.IO) {
            corpPortalApi.getTags().tags.map {
                TagItem(
                    id = it.tagId,
                    name = it.name,
                    textColor = it.textColor,
                    backgroundColor = it.backgroundColor,
                )
            }
        }
    }
}
