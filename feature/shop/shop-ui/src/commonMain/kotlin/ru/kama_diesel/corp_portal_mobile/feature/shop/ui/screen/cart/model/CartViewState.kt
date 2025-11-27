package ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.cart.model

import kotlinx.serialization.Serializable
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ShopItem

@Serializable
data class CartViewState(
    val cartItems: List<CartItemUIModel>,
    val shopItems: List<ShopItem>,
    val totalSum: Int?,
    val balance: Int?,
    val makingOrderState: MakingOrderState,
    val isLoading: Boolean,
)

@Serializable
data class CartItemUIModel(
    val inCartItemId: Int,
    val itemId: Int,
    val quantity: Int,
    val isChecked: Boolean,
    val cartAddingState: CartAddingState,
)

@Serializable
sealed class CartAddingState {
    @Serializable
    data object No : CartAddingState()

    @Serializable
    data object Adding : CartAddingState()
}

@Serializable
sealed class MakingOrderState {
    @Serializable
    data object No : MakingOrderState()

    @Serializable
    data object Process : MakingOrderState()
}
