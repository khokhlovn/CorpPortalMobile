package ru.kama_diesel.corp_portal_mobile.common.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CartResponseData(
    @SerialName("cart_data")
    val cartItems: List<CartItemData>? = null,
)

@Serializable
data class CartItemData(
    @SerialName("in_cart_item_id")
    val inCartItemId: Int,
    @SerialName("item_id")
    val itemId: Int,
    @SerialName("quantity")
    val quantity: Int,
)
