package ru.kama_diesel.corp_portal_mobile.feature.articles.data.repository

import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.data.network.api.CorpPortalApi
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IArticlesRepository
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ArticleItem

@Inject
class ArticlesRepository(
    private val corpPortalApi: CorpPortalApi,
) : IArticlesRepository {

    override suspend fun observeArticlesList(): List<ArticleItem> {
        return corpPortalApi.getArticles().articles.map {
            ArticleItem(
                id = it.postId,
                title = it.title,
                compressedText = it.compressedText,
                imagePath = it.images.first().path,
            )
        }
    }
}
