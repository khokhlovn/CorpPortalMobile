package ru.kama_diesel.corp_portal_mobile.common.data.network.api

import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.plugins.logging.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.util.network.*
import kotlinx.serialization.json.Json
import ru.kama_diesel.corp_portal_mobile.BuildKonfig

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object HttpClientBuilder {
    actual fun build(preferencesCookieStorage: CookiesStorage): HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    useAlternativeNames = false
                }
            )
            register(
                contentType = ContentType.Any,
                converter = KotlinxSerializationConverter(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            )
        }

        expectSuccess = true

        install(HttpRequestRetry) {
            maxRetries = 3
            retryOnExceptionIf { _, cause ->
                cause is UnresolvedAddressException || cause is HttpRequestTimeoutException
            }
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 3000
            connectTimeoutMillis = 3000
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Napier.v("HTTP Client", null, message)
                }
            }
            level = LogLevel.ALL
            sanitizeHeader { header -> header == HttpHeaders.Authorization }
        }

        install(HttpCookies) {
            storage = preferencesCookieStorage
        }

        defaultRequest {
            url(urlString = BuildKonfig.BASE_URL)
        }
    }
}
