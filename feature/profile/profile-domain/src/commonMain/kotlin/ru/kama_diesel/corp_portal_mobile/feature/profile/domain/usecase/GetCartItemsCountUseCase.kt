package ru.kama_diesel.corp_portal_mobile.feature.profile.domain.usecase

import io.ktor.client.plugins.*
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.ILogoutUseCase
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IProfileRepository

@Inject
class GetCartItemsCountUseCase(
    private val logoutUseCase: ILogoutUseCase,
    private val profileRepository: IProfileRepository,
) {
    suspend operator fun invoke(): Int {
        return try {
            profileRepository.getCartItemsCount()
        } catch (_: ClientRequestException) {
            logoutUseCase.invoke()
            0
        } catch (_: Exception) {
            0
        }
    }
}
