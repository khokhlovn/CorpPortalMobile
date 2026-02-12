package ru.kama_diesel.corp_portal_mobile.feature.profile.domain.usecase

import io.ktor.client.plugins.*
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.ILogoutUseCase
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IProfileRepository
import ru.kama_diesel.corp_portal_mobile.common.domain.model.MeItem

@Inject
class GetMyInfoUseCase(
    private val logoutUseCase: ILogoutUseCase,
    private val profileRepository: IProfileRepository,
) {
    suspend operator fun invoke(): MeItem? {
        return try {
            profileRepository.getMyInfo()
        } catch (_: ClientRequestException) {
            logoutUseCase.invoke()
            null
        } catch (_: Exception) {
            null
        }
    }
}
