package ru.kama_diesel.corp_portal_mobile.feature.reservation.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun ReservationScreenContainer(
    viewModel: ReservationViewModel,
) {
    val viewState by viewModel.viewState.collectAsState()

    ReservationScreen(
        viewState = viewState,
        mapState = viewModel.mapState,
        onOfficeChange = viewModel::onOfficeChange,
        onDateChange = viewModel::onDateChange,
        onSelectedPlaceChange = viewModel::onSelectedPlaceChange,
        onReserveClick = viewModel::onReserveClick,
        onConfirmReservationClick = viewModel::onConfirmReservationClick,
        onCloseConfirmationClick = viewModel::onCloseConfirmationClick,
    )
}
