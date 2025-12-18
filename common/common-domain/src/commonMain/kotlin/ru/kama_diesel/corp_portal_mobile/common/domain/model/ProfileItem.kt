package ru.kama_diesel.corp_portal_mobile.common.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ProfileItem(
    var username: String,
    var fullName: String,
    var position: String,
    var department: String,
    var mail: String,
    var mobile: String? = null,
    var chief: String? = null,
    var personalMobile: String? = null,
    var personalMail: String? = null
)
