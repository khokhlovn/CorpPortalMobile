package ru.kama_diesel.corp_portal_mobile.feature.shop.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import ru.kama_diesel.corp_portal_mobile.feature.shop.component.api.IShopComponentDependencies
import ru.kama_diesel.corp_portal_mobile.feature.shop.component.di.ShopFlowDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.shop.component.di.create

class ShopFlowComponent(
    componentContext: ComponentContext,
    componentDependencies: IShopComponentDependencies
) : ComponentContext by componentContext {

    private val diComponent = instanceKeeper.getOrCreate {
        ShopFlowDIComponent::class.create(componentDependencies)
    }

    val router = ShopFlowRouter(componentContext, diComponent)

    init {
        diComponent.getRouterHolder().updateInstance(router)
    }
}