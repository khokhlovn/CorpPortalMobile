package ru.kama_diesel.corp_portal_mobile.common.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MeResponseData(
    @SerialName("user")
    var user: UserData,
)

@Serializable
data class UserData(
    @SerialName("user_id")
    var userId: Int,
    @SerialName("username")
    var username: String,
    @SerialName("role")
    var role: Int,
    @SerialName("image_path")
    var imagePath: String? = null,
    @SerialName("balance")
    var balance: Int,
    @SerialName("gift_balance")
    var giftBalance: Int,
)