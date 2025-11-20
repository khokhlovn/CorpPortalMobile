package ru.kama_diesel.corp_portal_mobile.common.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CartItem(
    val inCartItemId: Int,
    val itemId: Int,
    val quantity: Int,
)
