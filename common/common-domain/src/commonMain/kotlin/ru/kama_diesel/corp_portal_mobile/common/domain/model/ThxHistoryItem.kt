package ru.kama_diesel.corp_portal_mobile.common.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ThxHistoryItem(
    val transactionId: Int,
    val userId: Int,
    val description: String,
    val date: String,
    val amount: Int,
    val creatorName: String,
    val recipientName: String?,
    val eventId: Int?,
)
