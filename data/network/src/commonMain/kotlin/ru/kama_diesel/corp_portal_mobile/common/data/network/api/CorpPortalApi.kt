package ru.kama_diesel.corp_portal_mobile.common.data.network.api

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.data.network.model.ArticlesResponseData
import ru.kama_diesel.corp_portal_mobile.common.data.network.model.LoginRequestData
import ru.kama_diesel.corp_portal_mobile.common.data.network.model.TagsResponseData

@Inject
class CorpPortalApi {

    private val httpClient = HttpClientBuilder.build()

    suspend fun login(loginRequestData: LoginRequestData): HttpResponse {
        return httpClient.post("login") {
            contentType(type = ContentType.Application.Json)
            setBody(body = loginRequestData)
        }
    }

    suspend fun getArticles(fromDate: Long?, toDate: Long?, selectedTagsIds: List<String>): ArticlesResponseData {
        println("getArticles: $fromDate $toDate $selectedTagsIds")
        return httpClient.get("articles") {
            url {
                fromDate?.let {
                    parameters.append("start", it.toString())
                }
                toDate?.let {
                    parameters.append("finish", it.toString())
                }
                selectedTagsIds.forEach {
                    parameters.append("tag_id", it)
                }
            }
        }.body()
    }

    suspend fun getTags(): TagsResponseData {
        return httpClient.get("tags").body()
    }
}
