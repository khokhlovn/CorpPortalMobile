package ru.kama_diesel.corp_portal_mobile.feature.reservation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import ovh.plrapps.mapcompose.core.TileStreamProvider
import ovh.plrapps.mapcompose.ui.state.MapState
import ru.kama_diesel.corp_portal_mobile.common.ui.component.LoadingDialog
import ru.kama_diesel.corp_portal_mobile.feature.reservation.ui.screen.model.ReservationDialog
import ru.kama_diesel.corp_portal_mobile.feature.reservation.ui.screen.model.ReservationViewState
import ru.kama_diesel.corp_portal_mobile.resources.Res
import ru.kama_diesel.corp_portal_mobile.resources.apply
import ru.kama_diesel.corp_portal_mobile.resources.article_tags
import ru.kama_diesel.corp_portal_mobile.resources.cancel
import ru.kama_diesel.corp_portal_mobile.resources.from_to
import ru.kama_diesel.corp_portal_mobile.resources.from_to_reserve
import ru.kama_diesel.corp_portal_mobile.resources.selected_place
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationScreen(
    viewState: ReservationViewState,
    mapState: MapState,
    onOfficeChange: (Office) -> Unit,
    onDateChange: (Long?, Long?) -> Unit,
    onSelectedPlaceChange: (String?) -> Unit,
    onReserveClick: () -> Unit,
    onConfirmReservationClick: () -> Unit,
    onCloseConfirmationClick: () -> Unit,
) {
    ReservationScreenContent(
        mapState = mapState,
        office = viewState.office,
        fromDate = viewState.fromDate,
        toDate = viewState.toDate,
        total = viewState.total,
        free = viewState.free,
        reserved = viewState.reserved,
        unavailable = viewState.unavailable,
        selectedPlace = viewState.selectedPlace,
        onOfficeChange = onOfficeChange,
        onDateChange = onDateChange,
        onSelectedPlaceChange = onSelectedPlaceChange,
        onReserveClick = onReserveClick,
    )

    when (val dialog = viewState.dialog) {
        is ReservationDialog.Confirmation -> {
            BasicAlertDialog(
                onDismissRequest = {},
            ) {
                Surface(
                    modifier = Modifier.widthIn(max = 300.dp).wrapContentHeight(),
                    shape = MaterialTheme.shapes.large,
                    color = MaterialTheme.colorScheme.inverseSurface,
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = dialog.selectedPlace,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.inverseOnSurface,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = convertMillisToDateRange(fromDate = viewState.fromDate, toDate = viewState.toDate),
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.scrim,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround,
                        ) {
                            TextButton(
                                colors = ButtonDefaults.textButtonColors().copy(contentColor = Color.Gray),
                                shape = RoundedCornerShape(12.dp),
                                onClick = onCloseConfirmationClick,
                            ) {
                                Text(text = stringResource(Res.string.cancel))
                            }
                            TextButton(
                                shape = RoundedCornerShape(12.dp),
                                onClick = onConfirmReservationClick,
                            ) {
                                Text(text = stringResource(Res.string.apply))
                            }
                        }
                    }
                }
            }
        }
        ReservationDialog.Loading -> LoadingDialog()
        ReservationDialog.No -> Unit
    }
}

@OptIn(ExperimentalTime::class, FormatStringsInDatetimeFormats::class)
@Composable
private fun convertMillisToDateRange(fromDate: Long?, toDate: Long?): String {
    val formatter = LocalDateTime.Format {
        byUnicodePattern("dd.MM.yyyy")
    }
    val fromDateString = fromDate?.let {
        Instant
            .fromEpochMilliseconds(fromDate)
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .format(formatter)

    }
    val toDateString = toDate?.let {
        Instant
            .fromEpochMilliseconds(toDate)
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .format(formatter)
    }

    return buildString {
        append(stringResource(Res.string.from_to_reserve, fromDateString ?: toDateString ?: "", toDateString ?: fromDateString ?: ""))
    }
}
