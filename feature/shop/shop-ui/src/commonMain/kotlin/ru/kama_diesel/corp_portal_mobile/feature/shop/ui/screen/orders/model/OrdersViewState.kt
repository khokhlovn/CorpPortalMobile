package ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.orders.model

import kotlinx.serialization.Serializable
import ru.kama_diesel.corp_portal_mobile.common.domain.model.OrderItem
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ShopItem

@Serializable
data class OrdersViewState(
    val orderItems: List<OrderItem>,
    val shopItems: List<ShopItem>,
    val isLoading: Boolean,
)
