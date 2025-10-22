package ru.kama_diesel.corp_portal_mobile.common.data.network.api

import io.ktor.client.*

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object HttpClientBuilder {
    fun build(): HttpClient
}
