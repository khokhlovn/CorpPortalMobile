package ru.kama_diesel.corp_portal_mobile.common.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class OrderItem(
    val id: Int,
    val date: String,
    val status: OrderStatus,
    val items: List<OrderPositionItem>,
)

@Serializable
enum class OrderStatus(val value: String) {
    Ordered("ordered"),
    Cancelled("cancelled"),
    Completed("completed"),
}

@Serializable
data class OrderPositionItem(
    val id: Int,
    val quantity: Int,
)
