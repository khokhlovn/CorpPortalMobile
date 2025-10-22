package ru.kama_diesel.corp_portal_mobile.common.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class TagItem(
    val id: String,
    val name: String,
    val textColor: String?,
    val backgroundColor: String?,
)
