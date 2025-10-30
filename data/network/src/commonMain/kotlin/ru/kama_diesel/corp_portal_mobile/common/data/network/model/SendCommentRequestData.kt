package ru.kama_diesel.corp_portal_mobile.common.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SendCommentRequestData(
    @SerialName("post_id")
    val postId: Int,

    @SerialName("text")
    val text: String,
)
