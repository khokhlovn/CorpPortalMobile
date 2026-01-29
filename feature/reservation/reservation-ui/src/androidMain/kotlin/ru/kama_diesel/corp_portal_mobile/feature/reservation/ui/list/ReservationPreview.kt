package ru.kama_diesel.corp_portal_mobile.feature.reservation.ui.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ovh.plrapps.mapcompose.core.TileStreamProvider
import ovh.plrapps.mapcompose.ui.state.MapState
import ru.kama_diesel.corp_portal_mobile.common.domain.model.TopWorkerItem
import ru.kama_diesel.corp_portal_mobile.common.ui.theme.AppTheme
import ru.kama_diesel.corp_portal_mobile.feature.reservation.ui.screen.Office
import ru.kama_diesel.corp_portal_mobile.feature.reservation.ui.screen.ReservationScreen
import ru.kama_diesel.corp_portal_mobile.feature.reservation.ui.screen.model.ReservationDialog
import ru.kama_diesel.corp_portal_mobile.feature.reservation.ui.screen.model.ReservationViewState

@Preview
@Composable
private fun ReservationScreenPreview() {
    AppTheme {
        ReservationScreen(
            viewState = ReservationViewState(
                reservationItems = listOf(),
                dialog = ReservationDialog.No,
                office = Office.FirstLeft,
                fromDate = null,
                toDate = null,
                total = 0,
                free = 0,
                reserved = 0,
                unavailable = 0,
                selectedPlace = null,
            ),
            mapState = MapState(levelCount = 5, fullWidth = 256, fullHeight = 256),
            onOfficeChange = {},
            onDateChange = { _, _ -> },
            onSelectedPlaceChange = {},
            onReserveClick = {},
            onConfirmReservationClick = {},
            onCloseConfirmationClick = {},
        )
    }
}
