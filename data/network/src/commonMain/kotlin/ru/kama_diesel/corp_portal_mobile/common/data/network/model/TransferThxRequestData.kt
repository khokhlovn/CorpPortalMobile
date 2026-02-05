package ru.kama_diesel.corp_portal_mobile.common.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransferThxRequestData(
    @SerialName("user_id")
    val userId: Int,

    @SerialName("amount")
    val amount: Int,
)
