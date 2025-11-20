package ru.kama_diesel.corp_portal_mobile.feature.shop.component.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import ru.kama_diesel.corp_portal_mobile.common.component.registerAndGetSavedState
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ShopItem
import ru.kama_diesel.corp_portal_mobile.feature.shop.component.di.ShopFlowDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.shop.component.list.di.CartDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.shop.component.list.di.create
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.cart.model.CartAddingState
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.cart.model.CartDialog
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.cart.model.CartViewState

class CartComponent(
    shopItems: List<ShopItem>,
    componentContext: ComponentContext,
    shopFlowDIComponent: ShopFlowDIComponent,
) : ComponentContext by componentContext {

    private val savedState: CartViewState = registerAndGetSavedState(
        key = CART_SAVED_STATE,
        initialValue = CartViewState(
            cartItems = listOf(),
            shopItems = shopItems,
            dialog = CartDialog.No,
            cartAddingState = CartAddingState.No,
            isLoading = true,
        ),
        deserialization = CartViewState.serializer(),
        serialization = CartViewState.serializer()
    ) {
        viewModel.currentState
    }

    private val diComponent = instanceKeeper.getOrCreate {
        CartDIComponent::class.create(shopFlowDIComponent, savedState)
    }

    val viewModel = diComponent.viewModel

    companion object Companion {
        private const val CART_SAVED_STATE = "CART_SAVED_STATE"
    }
}
