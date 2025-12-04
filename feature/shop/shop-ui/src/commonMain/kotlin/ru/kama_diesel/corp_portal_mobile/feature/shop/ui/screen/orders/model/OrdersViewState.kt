package ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.orders.model

import kotlinx.serialization.Serializable
import ru.kama_diesel.corp_portal_mobile.common.domain.model.OrderPositionItem
import ru.kama_diesel.corp_portal_mobile.common.domain.model.OrderStatus
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ShopItem
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.orders.Sorter

@Serializable
data class OrdersViewState(
    val orderItems: List<OrderItemUIModel>,
    val sortedOrderItems: List<OrderItemUIModel>,
    val shopItems: List<ShopItem>,
    val selectedSorter: Sorter,
    val isLoading: Boolean,
)

@Serializable
data class OrderItemUIModel(
    val id: Int,
    val date: String,
    val status: OrderStatus,
    val items: List<OrderPositionItem>,
    val totalSum: Int,
)
