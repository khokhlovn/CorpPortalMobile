package ru.kama_diesel.corp_portal_mobile.feature.articles.domain.usecase

import io.ktor.client.plugins.*
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IArticlesRepository
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.ILogoutUseCase
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ArticleItem

@Inject
class GetArticlesListUseCase(
    private val logoutUseCase: ILogoutUseCase,
    private val articlesRepository: IArticlesRepository,
) {
    suspend operator fun invoke(page: Int, fromDate: Long?, toDate: Long?, selectedTagsIds: List<String>): List<ArticleItem> {
        return try {
            articlesRepository.getArticlesList(
                page = page,
                fromDate = fromDate,
                toDate = toDate,
                selectedTagsIds = selectedTagsIds,
            )
        } catch (exception: ClientRequestException) {
            if (exception.response.status.value == 400) {
                throw exception
            } else {
                logoutUseCase()
                listOf()
            }
        }
    }
}
