package ru.kama_diesel.corp_portal_mobile.feature.root.component.di.dependencies

import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IAuthorizedUserRepository
import ru.kama_diesel.corp_portal_mobile.feature.auth.component.api.IAuthComponentDependencies
import ru.kama_diesel.corp_portal_mobile.feature.auth.domain.interfaces.IAuthCompletionUseCase

@Inject
internal class AuthComponentDependencies(
    lazyAuthCompletionUseCase: Lazy<IAuthCompletionUseCase>,
    lazyAuthorizedUserRepository: Lazy<IAuthorizedUserRepository>,
) : IAuthComponentDependencies {
    override val authCompletionUseCase by lazyAuthCompletionUseCase
    override val authorizedUserRepository by lazyAuthorizedUserRepository
}
