package ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.cart.model

import kotlinx.serialization.Serializable
import ru.kama_diesel.corp_portal_mobile.common.domain.model.CartItem
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ShopItem

@Serializable
data class CartViewState(
    val cartItems: List<CartItem>,
    val shopItems: List<ShopItem>,
    val dialog: CartDialog,
    val cartAddingState: CartAddingState,
    val isLoading: Boolean,
)

@Serializable
sealed class CartDialog {
    @Serializable
    data object No : CartDialog()

    @Serializable
    data object Loading : CartDialog()
}

@Serializable
sealed class CartAddingState {
    @Serializable
    data object No : CartAddingState()

    @Serializable
    data object Adding : CartAddingState()

    @Serializable
    data object Success : CartAddingState()
}
