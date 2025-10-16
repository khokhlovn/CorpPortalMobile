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
    @SerialName("compressed_text")
    val compressedText: String,
    @SerialName("images")
    val images: List<Image>,
)

@Serializable
data class Image(
    @SerialName("path")
    val path: String,
)
