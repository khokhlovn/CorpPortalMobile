package ru.kama_diesel.corp_portal_mobile.common.data.network.api

import io.ktor.client.*
import io.ktor.client.plugins.cookies.*

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object HttpClientBuilder {
    fun build(preferencesCookieStorage: CookiesStorage): HttpClient
}
