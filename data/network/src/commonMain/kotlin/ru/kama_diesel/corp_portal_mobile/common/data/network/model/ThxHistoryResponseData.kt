package ru.kama_diesel.corp_portal_mobile.common.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ThxHistoryResponseData(
    @SerialName("transactions")
    val transactions: List<TransactionsData>? = null,
)

@Serializable
data class TransactionsData(
    @SerialName("transaction_id")
    val transactionId: Int,
    @SerialName("user_id")
    val userId: Int,
    @SerialName("description")
    val description: String,
    @SerialName("date")
    val date: String,
    @SerialName("amount")
    val amount: Int,
    @SerialName("creator_name")
    val creatorName: String,
    @SerialName("recipient_name")
    val recipientName: String? = null,
    @SerialName("event_id")
    val eventId: Int? = null,
)
