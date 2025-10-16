package ru.kama_diesel.corp_portal_mobile.feature.auth.domain.usecase

import io.ktor.client.plugins.*
import io.ktor.util.network.*
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IAuthorizedUserRepository
import ru.kama_diesel.corp_portal_mobile.feature.auth.domain.data.AuthResult
import ru.kama_diesel.corp_portal_mobile.feature.auth.domain.interfaces.IUserRepository

@Inject
class AuthorizeUseCase(
    private val userRepository: IUserRepository,
    private val authorizedIUserRepository: IAuthorizedUserRepository
) {
    suspend operator fun invoke(username: String, password: String): AuthResult {
        return try {
            userRepository.login(username = username, password = password)
            authorizedIUserRepository.setUserAuthorized()
            AuthResult.SUCCESS
        } catch (_: UnresolvedAddressException) {
            AuthResult.NO_INTERNET
        } catch (_: ClientRequestException) {
            AuthResult.BAD_CREDENTIAL
        }
    }
}