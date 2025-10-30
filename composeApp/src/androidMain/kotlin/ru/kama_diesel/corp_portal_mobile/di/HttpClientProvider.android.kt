package ru.kama_diesel.corp_portal_mobile.di

import io.ktor.client.*
import ru.kama_diesel.corp_portal_mobile.common.data.network.api.HttpClientBuilder
import ru.kama_diesel.corp_portal_mobile.common.data.network.api.PreferencesCookieStorage
import ru.kama_diesel.corp_portal_mobile.common.data.preferences.IPreferences

fun getHttpClient(preferences: IPreferences): HttpClient {
    return HttpClientBuilder.build(preferencesCookieStorage = PreferencesCookieStorage(preferences))
}
