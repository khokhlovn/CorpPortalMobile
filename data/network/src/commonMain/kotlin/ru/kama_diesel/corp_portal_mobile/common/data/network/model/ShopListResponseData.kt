package ru.kama_diesel.corp_portal_mobile.common.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class ShopListResponseData(
    @SerialName("shop_list")
    val shopItems: List<ShopItemData>? = null,
)

@Serializable
data class ShopItemData(
    @SerialName("item_id")
    val itemId: Int,
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String,
    @SerialName("characteristics")
    val characteristics: JsonObject,
    @SerialName("part_number")
    val partNumber: String? = null,
    @SerialName("price")
    val price: Int,
    @SerialName("photo_paths")
    val photoPaths: List<String>? = null,
    @SerialName("is_available")
    val isAvailable: Boolean? = null,
    @SerialName("is_active")
    val isActive: Boolean? = null,
)
