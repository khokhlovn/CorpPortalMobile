package ru.kama_diesel.corp_portal_mobile.common.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticlesResponseData(
    @SerialName("articles")
    val articles: List<ArticleData>? = null,
)

@Serializable
data class ArticleData(
    @SerialName("post_id")
    val postId: String,
    @SerialName("title")
    val title: String,
    @SerialName("text")
    val text: String,
    @SerialName("creation_date")
    val creationDate: String,
    @SerialName("images")
    val imagesPaths: List<String>? = null,
    @SerialName("tags")
    val tags: List<TagData>? = null,
)
