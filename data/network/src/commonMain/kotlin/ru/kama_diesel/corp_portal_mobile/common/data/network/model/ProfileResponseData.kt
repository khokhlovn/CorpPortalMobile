package ru.kama_diesel.corp_portal_mobile.common.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponseData(
    @SerialName("profile")
    val profile: ProfileData,
)

@Serializable
data class ProfileData(
    @SerialName("username")
    var username: String,
    @SerialName("full_name")
    var fullName: String,
    @SerialName("position")
    var position: String,
    @SerialName("department")
    var department: String,
    @SerialName("mail")
    var mail: String,
    @SerialName("mobile")
    var mobile: String? = null,
    @SerialName("chief")
    var chief: String? = null,
    @SerialName("personal_mobile")
    var personalMobile: String? = null,
    @SerialName("personal_mail")
    var personalMail: String? = null,
)
