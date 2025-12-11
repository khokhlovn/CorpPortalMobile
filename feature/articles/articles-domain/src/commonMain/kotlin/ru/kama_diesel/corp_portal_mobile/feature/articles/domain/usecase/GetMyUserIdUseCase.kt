package ru.kama_diesel.corp_portal_mobile.feature.articles.domain.usecase

import io.ktor.client.plugins.*
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IArticlesRepository
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.ILogoutUseCase

@Inject
class GetMyUserIdUseCase(
    private val logoutUseCase: ILogoutUseCase,
    private val articlesRepository: IArticlesRepository,
) {
    suspend operator fun invoke(): Int {
        return try {
            articlesRepository.getMyUserId()
        } catch (_: ClientRequestException) {
            logoutUseCase.invoke()
            0
        } catch (_: Exception) {
            0
        }
    }
}
