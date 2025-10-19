package ru.kama_diesel.corp_portal_mobile.feature.auth.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.data.network.api.CorpPortalApi
import ru.kama_diesel.corp_portal_mobile.common.data.network.model.LoginRequestData
import ru.kama_diesel.corp_portal_mobile.feature.auth.domain.interfaces.IUserRepository

@Inject
class UserRepository(
    private val api: CorpPortalApi,
) : IUserRepository {
    override suspend fun login(
        username: String,
        password: String,
    ) {
        withContext(Dispatchers.IO) {
            api.login(
                loginRequestData = LoginRequestData(
                    username = username,
                    password = password,
                )
            )
        }
    }
}
