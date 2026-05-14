package ru.kama_diesel.corp_portal_mobile.common.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopWorkersListResponseData(
    @SerialName("wall_of_fame")
    val wallOfFame: List<WallOfFlameData>? = null,
)

@Serializable
data class WallOfFlameData(
    @SerialName("year")
    val year: Int,
    @SerialName("top_workers")
    val workersInfo: List<WorkerInfoData>? = null,
)

@Serializable
data class WorkerInfoData(
    @SerialName("full_name")
    val fullName: String,
    @SerialName("position")
    val position: String,
    @SerialName("link")
    val link: String? = null,
    @SerialName("image_path")
    val imagePath: String? = null,
)
