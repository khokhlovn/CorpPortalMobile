package ru.kama_diesel.corp_portal_mobile.common.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DropCartItemRequestData(
    @SerialName("in_cart_item_id")
    val inCartItemId: Int,
)
