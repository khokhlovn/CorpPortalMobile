package ru.kama_diesel.corp_portal_mobile.common.data.network.model

import kotlinx.io.files.Path
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopWorkersListResponseData(
    @SerialName("workers_info")
    val workersInfo: List<WorkerInfoData>? = null,
)

@Serializable
data class WorkerInfoData(
    @SerialName("full_name")
    val fullName: String,
    @SerialName("position")
    val position: String,
    @SerialName("link")
    val link: String,
    @SerialName("image_path")
    val imagePath: String?,
)
