package ru.kama_diesel.corp_portal_mobile.common.data.network.api

import io.ktor.client.*

expect object HttpClientBuilder {
    fun build(): HttpClient
}
