package ru.kama_diesel.corp_portal_mobile.common.data.repository

import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.data.preferences.IPreferences
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IAuthorizedUserRepository

@Inject
class AuthorizedUserRepository(
    private val preferences: IPreferences,
) : IAuthorizedUserRepository {
    override suspend fun isUserAuthorized(): Boolean {
        return preferences.getBoolean(IS_USER_AUTHORIZED)
    }

    override suspend fun setUserAuthorized() {
        preferences.putBoolean(IS_USER_AUTHORIZED, true)
    }

    override fun logout() {
        preferences.clear()
    }

    companion object {
        private const val IS_USER_AUTHORIZED = "IS_USER_AUTHORIZED"
    }
}