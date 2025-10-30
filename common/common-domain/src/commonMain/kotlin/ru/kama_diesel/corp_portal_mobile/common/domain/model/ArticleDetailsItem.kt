package ru.kama_diesel.corp_portal_mobile.common.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ArticleDetailsItem(
    val text: String,
    val comments: List<CommentItem>?
)

@Serializable
data class CommentItem(
    val commentId: Int,
    val userId: Int,
    val text: String,
    val creationDate: String,
    val fullName: String,
    val position: String,
    val department: String,
    val imagePath: String?,
)
