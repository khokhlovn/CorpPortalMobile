package ru.kama_diesel.corp_portal_mobile.feature.root.domain.usecase

import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.feature.auth.domain.interfaces.IAuthCompletionUseCase
import ru.kama_diesel.corp_portal_mobile.feature.root.domain.fsm.RootFeature

@Inject
class AuthCompletionUseCase(
    private val rootFeature: RootFeature,
) : IAuthCompletionUseCase {
    override suspend operator fun invoke(name: String) {
        rootFeature.login()
    }
}