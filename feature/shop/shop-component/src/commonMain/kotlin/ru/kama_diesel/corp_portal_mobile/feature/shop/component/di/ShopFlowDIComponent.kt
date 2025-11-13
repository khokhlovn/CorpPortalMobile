package ru.kama_diesel.corp_portal_mobile.feature.shop.component.di

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IShopRepository
import ru.kama_diesel.corp_portal_mobile.common.ui.navigation.RouterHolder
import ru.kama_diesel.corp_portal_mobile.feature.shop.component.api.IShopComponentDependencies
import ru.kama_diesel.corp_portal_mobile.feature.shop.data.repository.ShopRepository
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.api.IShopFlowRouter

@Component
abstract class ShopFlowDIComponent(
    @Component val dependencies: IShopComponentDependencies
) : InstanceKeeper.Instance {

    private val routerHolder = RouterHolder<IShopFlowRouter>()

    @Provides
    fun getRouterHolder(): RouterHolder<IShopFlowRouter> = routerHolder

    val ShopRepository.bind: IShopRepository
        @Provides get() = this
}
