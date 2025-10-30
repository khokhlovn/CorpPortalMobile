package ru.kama_diesel.corp_portal_mobile.feature.auth.component.api

import io.ktor.client.*
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IAuthorizedUserRepository
import ru.kama_diesel.corp_portal_mobile.feature.auth.domain.interfaces.IAuthCompletionUseCase

interface IAuthComponentDependencies {
    val authCompletionUseCase: IAuthCompletionUseCase
    val authorizedUserRepository: IAuthorizedUserRepository
    val httpClient: HttpClient
}