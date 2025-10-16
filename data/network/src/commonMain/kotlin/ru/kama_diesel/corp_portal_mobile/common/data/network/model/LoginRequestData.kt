package ru.kama_diesel.corp_portal_mobile.common.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestData(
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val password: String,
)
