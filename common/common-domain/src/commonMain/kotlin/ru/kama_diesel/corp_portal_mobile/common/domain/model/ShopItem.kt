package ru.kama_diesel.corp_portal_mobile.common.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ShopItem(
    val id: Int,
    val name: String,
    val description: String,
    val characteristics: Map<String, String>,
    val partNumber: String?,
    val price: Int,
    val imagePaths: List<String>?,
    val isAvailable: Boolean,
    val isActive: Boolean,
    val quantity: Int,
)
