package ru.kama_diesel.corp_portal_mobile.feature.articles.domain.usecase

import io.ktor.client.plugins.*
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IArticlesRepository
import ru.kama_diesel.corp_portal_mobile.feature.articles.domain.api.ILogoutUseCase

@Inject
class LikeUseCase(
    private val logoutUseCase: ILogoutUseCase,
    private val articlesRepository: IArticlesRepository,
) {
    suspend operator fun invoke(postId: String): Boolean {
        return try {
            articlesRepository.like(postId = postId)
            true
        } catch (_: ClientRequestException) {
            logoutUseCase.invoke()
            false
        } catch (_: Exception) {
            false
        }
    }
}
