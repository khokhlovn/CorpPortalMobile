package ru.kama_diesel.corp_portal_mobile.common.data.network.api

import io.github.aakira.napier.DebugAntilog
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
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.BuildKonfig
import ru.kama_diesel.corp_portal_mobile.common.data.network.model.ArticlesResponseData
import ru.kama_diesel.corp_portal_mobile.common.data.network.model.LoginRequestData

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
        }

        expectSuccess = true

        install(HttpTimeout) {
            requestTimeoutMillis = 5000
            connectTimeoutMillis = 10000
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
    }.also { Napier.base(DebugAntilog()) }

    suspend fun login(loginRequestData: LoginRequestData): HttpResponse {
        return httpClient.post("login") {
            contentType(type = ContentType.Application.Json)
            setBody(body = loginRequestData)
        }
    }

    suspend fun getArticles(): ArticlesResponseData {
        return httpClient.get("articles").body()
    }
}
