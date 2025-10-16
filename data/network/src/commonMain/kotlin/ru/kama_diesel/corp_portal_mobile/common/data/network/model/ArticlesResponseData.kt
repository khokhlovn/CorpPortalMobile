package ru.kama_diesel.corp_portal_mobile.common.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticlesResponseData(
    @SerialName("articles")
    val articles: List<ArticleData>,
)

@Serializable
data class ArticleData(
    @SerialName("post_id")
    val postId: String,
    @SerialName("title")
    val title: String,
    @SerialName("text")
    val text: String,
    @SerialName("images")
    val imagesPaths: List<String>?,
)
