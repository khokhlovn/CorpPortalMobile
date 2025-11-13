package ru.kama_diesel.corp_portal_mobile.feature.main.component.di.dependencies

import io.ktor.client.*
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.feature.shop.component.api.IShopComponentDependencies

@Inject
internal class ShopComponentDependencies(
    lazyHttpClient: Lazy<HttpClient>,
) : IShopComponentDependencies {
    override val httpClient by lazyHttpClient
}
