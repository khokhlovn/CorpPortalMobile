package ru.kama_diesel.corp_portal_mobile.feature.profile.domain.usecase

import io.ktor.client.plugins.*
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.ILogoutUseCase
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IProfileRepository

@Inject
class TakeAwardUseCase(
    private val logoutUseCase: ILogoutUseCase,
    private val profileRepository: IProfileRepository,
) {
    suspend operator fun invoke(): Boolean? {
        return try {
            profileRepository.getWeeklyThx()
            true
        } catch (e: ClientRequestException) {
            if (e.response.status.value == 406) {
                false
            } else {
                logoutUseCase.invoke()
                null
            }
        } catch (_: Exception) {
            null
        }
    }
}
