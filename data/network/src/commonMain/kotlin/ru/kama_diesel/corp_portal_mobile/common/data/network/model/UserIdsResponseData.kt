package ru.kama_diesel.corp_portal_mobile.common.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserIdsResponseData(
    @SerialName("users")
    val users: List<UserIdWithNameData>? = null,
)

@Serializable
data class UserIdWithNameData(
    @SerialName("full_name")
    val fullName: String,
    @SerialName("user_id")
    val userId: Int,
)