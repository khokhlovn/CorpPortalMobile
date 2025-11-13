package ru.kama_diesel.corp_portal_mobile.feature.shop.component.api

import io.ktor.client.*

interface IShopComponentDependencies {
    val httpClient: HttpClient
}
