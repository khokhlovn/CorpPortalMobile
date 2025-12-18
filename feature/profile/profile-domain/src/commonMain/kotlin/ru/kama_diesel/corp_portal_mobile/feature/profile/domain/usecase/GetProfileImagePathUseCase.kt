package ru.kama_diesel.corp_portal_mobile.feature.profile.domain.usecase

import io.ktor.client.plugins.*
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.ILogoutUseCase
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IProfileRepository

@Inject
class GetProfileImagePathUseCase(
    private val logoutUseCase: ILogoutUseCase,
    private val profileRepository: IProfileRepository,
) {
    suspend operator fun invoke(): String? {
        return try {
            profileRepository.getProfileImagePath()
        } catch (_: ClientRequestException) {
            logoutUseCase.invoke()
            null
        } catch (_: Exception) {
            null
        }
    }
}
