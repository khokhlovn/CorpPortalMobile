package ru.kama_diesel.corp_portal_mobile.common.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class TopWorkerItem(
    val fullName: String,
    val position: String,
    val link: String,
    val imagePath: String?,
)
