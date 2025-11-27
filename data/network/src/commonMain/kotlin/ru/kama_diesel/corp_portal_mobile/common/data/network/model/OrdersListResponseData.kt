package ru.kama_diesel.corp_portal_mobile.common.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrdersListResponseData(
    @SerialName("carts")
    val carts: List<CartData>? = null,
)

@Serializable
data class CartData(
    @SerialName("cart_id")
    val cartId: Int,
    @SerialName("date")
    val date: String,
    @SerialName("status")
    val status: String,
    @SerialName("items")
    val items: List<ItemData>,
)

@Serializable
data class ItemData(
    @SerialName("item_id")
    val itemId: Int,
    @SerialName("quantity")
    val quantity: Int,
)
