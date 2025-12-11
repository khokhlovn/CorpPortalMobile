package ru.kama_diesel.corp_portal_mobile.feature.articles.domain.usecase

import io.ktor.client.plugins.*
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IArticlesRepository
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.ILogoutUseCase
import ru.kama_diesel.corp_portal_mobile.common.domain.model.TagItem

@Inject
class GetTagsUseCase(
    private val logoutUseCase: ILogoutUseCase,
    private val articlesRepository: IArticlesRepository,
) {
    suspend operator fun invoke(): List<TagItem> {
        return try {
            articlesRepository.getTagsList()
        } catch (_: ClientRequestException) {
            logoutUseCase.invoke()
            listOf()
        } catch (_: Exception) {
            listOf()
        }
    }
}
