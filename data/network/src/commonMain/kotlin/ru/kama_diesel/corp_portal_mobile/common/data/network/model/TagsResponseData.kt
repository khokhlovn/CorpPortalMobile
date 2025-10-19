package ru.kama_diesel.corp_portal_mobile.common.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TagsResponseData(
    @SerialName("tags")
    val tags: List<TagData>,
)

@Serializable
data class TagData(
    @SerialName("tag_id")
    val tagId: String,
    @SerialName("name")
    val name: String,
    @SerialName("text_color")
    val textColor: String? = null,
    @SerialName("background_color")
    val backgroundColor: String? = null,
)
