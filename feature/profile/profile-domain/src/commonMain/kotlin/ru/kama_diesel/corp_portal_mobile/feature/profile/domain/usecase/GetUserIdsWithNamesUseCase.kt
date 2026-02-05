package ru.kama_diesel.corp_portal_mobile.feature.profile.domain.usecase

import io.ktor.client.plugins.ClientRequestException
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.ILogoutUseCase
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IProfileRepository
import ru.kama_diesel.corp_portal_mobile.common.domain.model.UserIdWithNameItem

@Inject
class GetUserIdsWithNamesUseCase(
    private val logoutUseCase: ILogoutUseCase,
    private val profileRepository: IProfileRepository,
) {
    suspend operator fun invoke(): List<UserIdWithNameItem> {
        return try {
            profileRepository.getUserIdsWithNames()
        } catch (_: ClientRequestException) {
            logoutUseCase.invoke()
            listOf()
        } catch (_: Exception) {
            listOf()
        }
    }
}
