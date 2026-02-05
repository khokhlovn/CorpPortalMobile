package ru.kama_diesel.corp_portal_mobile.common.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserIdWithNameItem(
    var userId: Int,
    var fullName: String,
)
