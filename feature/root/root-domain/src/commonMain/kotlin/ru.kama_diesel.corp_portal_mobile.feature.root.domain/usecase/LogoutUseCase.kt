package ru.kama_diesel.corp_portal_mobile.feature.root.domain.usecase

import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IAuthorizedUserRepository
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.ILogoutUseCase
import ru.kama_diesel.corp_portal_mobile.feature.root.domain.fsm.RootFeature

@Inject
class LogoutUseCase(
    private val rootFeature: RootFeature,
    private val authorizedUserRepository: IAuthorizedUserRepository
) : ILogoutUseCase {
    override fun invoke() {
        authorizedUserRepository.logout()
        rootFeature.logout()
    }
}
