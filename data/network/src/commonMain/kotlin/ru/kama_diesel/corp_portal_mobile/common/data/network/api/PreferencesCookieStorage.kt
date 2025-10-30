package ru.kama_diesel.corp_portal_mobile.common.data.network.api

import io.ktor.client.plugins.cookies.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import ru.kama_diesel.corp_portal_mobile.common.data.preferences.IPreferences

class PreferencesCookieStorage(
    val preferences: IPreferences,
) : CookiesStorage {

    override suspend fun get(requestUrl: Url): List<Cookie> {
        return try {
            Json.decodeFromString<List<Cookie>>(preferences.getString("cookies"))
        } catch (_: Exception) {
            listOf()
        }
    }

    override suspend fun addCookie(requestUrl: Url, cookie: Cookie) {
        try {
            val existingList = Json.decodeFromString<List<Cookie>>(preferences.getString("cookies"))
            val mutable = existingList.toMutableList()
            if (existingList.any { it.name == cookie.name }) {
                mutable.removeAll { it.name == cookie.name }
            }
            mutable.add(cookie.copy(path = "/api"))
            preferences.putString("cookies", Json.encodeToString(mutable))
        } catch (_: Exception) {
            preferences.putString("cookies", Json.encodeToString(listOf(cookie.copy(path = "/api"))))
        }
    }

    override fun close() {
    }
}
