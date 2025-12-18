package ru.kama_diesel.corp_portal_mobile.common.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReservationListResponseData(
    @SerialName("reservation_list")
    val reservationList: List<ReservationItemData>? = null,
)

@Serializable
data class ReservationItemData(
    @SerialName("place_id")
    var placeId: Int,
    @SerialName("name")
    var name: String,
    @SerialName("is_available")
    var isAvailable: Boolean,
    @SerialName("user_id")
    var userId: Int,
    @SerialName("start")
    var start: Long? = null,
    @SerialName("finish")
    var finish: Long? = null,
    @SerialName("full_name")
    var fullName: String,
    @SerialName("position")
    var position: String,
    @SerialName("department")
    var department: String,
    @SerialName("mail")
    var mail: String,
    @SerialName("mobile")
    var mobile: String? = null,
    @SerialName("phone")
    var phone: String? = null,
    @SerialName("image_path")
    var imagePath: String? = null,
)
