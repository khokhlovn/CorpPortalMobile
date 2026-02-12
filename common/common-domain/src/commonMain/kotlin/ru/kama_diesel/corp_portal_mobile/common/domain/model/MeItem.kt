package ru.kama_diesel.corp_portal_mobile.common.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class MeItem(
    val userId: Int,
    val username: String,
    val role: Int,
    val imagePath: String?,
    val balance: Int,
    val giftBalance: Int,
    val weeklyAward: Int,
)
