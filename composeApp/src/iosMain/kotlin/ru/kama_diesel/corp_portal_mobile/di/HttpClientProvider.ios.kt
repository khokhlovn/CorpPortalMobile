package ru.kama_diesel.corp_portal_mobile.di

import io.ktor.client.*
import io.ktor.client.plugins.cookies.*
import ru.kama_diesel.corp_portal_mobile.common.data.network.api.HttpClientBuilder

fun getHttpClient(preferencesCookieStorage: CookiesStorage): HttpClient {
    return HttpClientBuilder.build(preferencesCookieStorage = preferencesCookieStorage)
}
