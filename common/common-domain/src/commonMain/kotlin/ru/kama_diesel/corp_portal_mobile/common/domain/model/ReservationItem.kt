package ru.kama_diesel.corp_portal_mobile.common.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ReservationItem(
    val placeId: Int,
    val name: String,
    val isAvailable: Boolean,
    val userId: Int,
    val start: String?,
    val finish: String?,
    val fullName: String,
    val position: String,
    val department: String,
    val mail: String,
    val mobile: String?,
    val imagePath: String?,
)
