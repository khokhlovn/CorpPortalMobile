package ru.kama_diesel.corp_portal_mobile.common.data.network.api

import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.util.network.*
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.BuildKonfig
import ru.kama_diesel.corp_portal_mobile.common.data.network.model.ArticlesResponseData
import ru.kama_diesel.corp_portal_mobile.common.data.network.model.LoginRequestData
import ru.kama_diesel.corp_portal_mobile.common.data.network.model.TagsResponseData

@Inject
class CorpPortalApi {

    private val httpClient = HttpClient(CIO) {
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

        defaultRequest {
            url(urlString = BuildKonfig.BASE_URL)
        }
    }

    suspend fun login(loginRequestData: LoginRequestData): HttpResponse {
        return httpClient.post("login") {
            contentType(type = ContentType.Application.Json)
            setBody(body = loginRequestData)
        }
    }

    suspend fun getArticles(): ArticlesResponseData {
        return httpClient.get("articles").body()
    }

    suspend fun getTags(): TagsResponseData {
        return httpClient.get("tags").body()
    }
}
