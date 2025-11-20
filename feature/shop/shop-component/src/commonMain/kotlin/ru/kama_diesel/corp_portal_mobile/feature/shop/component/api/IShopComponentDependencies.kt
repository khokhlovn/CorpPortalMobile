package ru.kama_diesel.corp_portal_mobile.feature.shop.component.api

import io.ktor.client.*
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.ILogoutUseCase

interface IShopComponentDependencies {
    val logoutUseCase: ILogoutUseCase
    val httpClient: HttpClient
}
