package ru.kama_diesel.corp_portal_mobile.common.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleDetailsResponseData(
    @SerialName("article")
    val article: ArticleDetailsData,
)

@Serializable
data class ArticleDetailsData(
    @SerialName("text")
    val text: String,
    @SerialName("comments")
    val comments: List<CommentData>? = null,
)

@Serializable
data class CommentData(
    @SerialName("comment_id")
    val commentId: Int,
    @SerialName("user_id")
    val userId: Int,
    @SerialName("reply_to")
    val replyTo: Int? = null,
    @SerialName("text")
    val text: String,
    @SerialName("creation_date")
    val creationDate: String,
    @SerialName("full_name")
    val fullName: String,
    @SerialName("position")
    val position: String,
    @SerialName("department")
    val department: String,
    @SerialName("image_path")
    val imagePath: String? = null,
    @SerialName("likes_amount")
    val likesAmount: Int,
    @SerialName("is_liked")
    val isLiked: Boolean,
)
