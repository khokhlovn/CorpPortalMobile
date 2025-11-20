package ru.kama_diesel.corp_portal_mobile.feature.main.component.di.dependencies

import io.ktor.client.*
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.ILogoutUseCase
import ru.kama_diesel.corp_portal_mobile.feature.shop.component.api.IShopComponentDependencies

@Inject
internal class ShopComponentDependencies(
    lazyLogoutUseCase: Lazy<ILogoutUseCase>,
    lazyHttpClient: Lazy<HttpClient>,
) : IShopComponentDependencies {
    override val logoutUseCase by lazyLogoutUseCase
    override val httpClient by lazyHttpClient
}
