package ru.kama_diesel.corp_portal_mobile.feature.shop.component.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import ru.kama_diesel.corp_portal_mobile.common.component.registerAndGetSavedState
import ru.kama_diesel.corp_portal_mobile.feature.shop.component.di.ShopFlowDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.shop.component.list.di.OrdersDiComponent
import ru.kama_diesel.corp_portal_mobile.feature.shop.component.list.di.create
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.orders.model.OrdersViewState

class OrdersComponent(
    componentContext: ComponentContext,
    shopFlowDIComponent: ShopFlowDIComponent,
) : ComponentContext by componentContext {

    private val savedState: OrdersViewState = registerAndGetSavedState(
        key = ORDERS_SAVED_STATE,
        initialValue = OrdersViewState(
            orderItems = listOf(),
            shopItems = listOf(),
            isLoading = true,
        ),
        deserialization = OrdersViewState.serializer(),
        serialization = OrdersViewState.serializer()
    ) {
        viewModel.currentState
    }

    private val diComponent = instanceKeeper.getOrCreate {
        OrdersDiComponent::class.create(shopFlowDIComponent, savedState)
    }

    val viewModel = diComponent.viewModel

    companion object Companion {
        private const val ORDERS_SAVED_STATE = "ORDERS_SAVED_STATE"
    }
}