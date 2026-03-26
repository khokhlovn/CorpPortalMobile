package ru.kama_diesel.corp_portal_mobile.feature.articles.domain.usecase

import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IArticlesRepository
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ArticleDetailsItem

@Inject
class GetArticleDetailsUseCase(
    private val articlesRepository: IArticlesRepository,
) {
    suspend operator fun invoke(articleId: String, userId: String): ArticleDetailsItem {
        return articlesRepository.getArticleDetails(articleId = articleId, userId = userId)
    }
}
