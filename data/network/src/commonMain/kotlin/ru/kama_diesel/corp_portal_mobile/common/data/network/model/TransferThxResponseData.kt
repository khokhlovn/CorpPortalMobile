package ru.kama_diesel.corp_portal_mobile.common.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransferThxResponseData(
    @SerialName("status")
    val status: String,
    @SerialName("error")
    val error: String? = null,
)
