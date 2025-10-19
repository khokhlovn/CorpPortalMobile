package ru.kama_diesel.corp_portal_mobile.feature.articles.domain.usecase

import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IArticlesRepository
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ArticleItem

@Inject
class GetArticlesListUseCase(
    private val articlesRepository: IArticlesRepository,
) {
    suspend operator fun invoke(): List<ArticleItem> = articlesRepository.getArticlesList()
}
