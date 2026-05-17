package ru.kama_diesel.corp_portal_mobile.common.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class WallOfFameItem(
    val year: Int,
    val topWorkerItems: List<TopWorkerItem>,
)
