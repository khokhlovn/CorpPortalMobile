package ru.kama_diesel.corp_portal_mobile.common.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ArticleItem(
    val id: String,
    val title: String,
    val text: String,
    val imagePaths: List<String>?,
    val tags: List<String>?,
    val creationDate: String,
)
