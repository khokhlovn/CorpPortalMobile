package ru.kama_diesel.corp_portal_mobile.feature.profile.domain.usecase

import io.ktor.client.plugins.ClientRequestException
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IArticlesRepository
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.ILogoutUseCase
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IProfileRepository

@Inject
class TransferThxUseCase(
    private val logoutUseCase: ILogoutUseCase,
    private val profileRepository: IProfileRepository,
) {
    suspend operator fun invoke(userId: Int, amount: Int): Boolean {
        return try {
            profileRepository.transferThx(userId = userId, amount = amount)
            true
        } catch (_: ClientRequestException) {
            logoutUseCase.invoke()
            false
        } catch (_: Exception) {
            false
        }
    }
}
