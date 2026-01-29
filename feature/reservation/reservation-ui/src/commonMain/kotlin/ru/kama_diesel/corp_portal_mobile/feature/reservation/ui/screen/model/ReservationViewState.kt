package ru.kama_diesel.corp_portal_mobile.feature.reservation.ui.screen.model

import kotlinx.serialization.Serializable
import ovh.plrapps.mapcompose.ui.state.MapState
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ReservationItem
import ru.kama_diesel.corp_portal_mobile.common.domain.model.TopWorkerItem
import ru.kama_diesel.corp_portal_mobile.feature.reservation.ui.screen.Office

@Serializable
data class ReservationViewState(
    val reservationItems: List<ReservationItem>,
    val total: Int,
    val free: Int,
    val reserved: Int,
    val unavailable: Int,
    val dialog: ReservationDialog,
    val office: Office,
    val fromDate: Long?,
    val toDate: Long?,
    val selectedPlace: String?,
)

@Serializable
sealed class ReservationDialog {
    @Serializable
    data object No : ReservationDialog()

    @Serializable
    data object Loading : ReservationDialog()

    @Serializable
    data class Confirmation(val selectedPlace: String) : ReservationDialog()
}
