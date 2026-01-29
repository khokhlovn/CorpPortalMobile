package ru.kama_diesel.corp_portal_mobile.feature.reservation.ui.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.kama_diesel.corp_portal_mobile.common.ui.theme.AppTheme
import ru.kama_diesel.corp_portal_mobile.feature.reservation.ui.screen.Office
import ru.kama_diesel.corp_portal_mobile.feature.reservation.ui.screen.ReservationFilterPanel

@Preview
@Composable
private fun ReservationFilterPanelPreview() {
    AppTheme {
        ReservationFilterPanel(
            office = Office.FirstLeft,
            fromDate = null,
            toDate = null,
            onOfficeChange = {},
            onDateChange = { _, _ -> },
        )
    }
}
