package ru.kama_diesel.corp_portal_mobile.common.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddToCartRequestData(
    @SerialName("item_id")
    val itemId: Int,

    @SerialName("quantity")
    val quantity: Int,
)
