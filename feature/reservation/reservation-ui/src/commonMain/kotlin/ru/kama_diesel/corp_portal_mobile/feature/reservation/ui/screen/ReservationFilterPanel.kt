package ru.kama_diesel.corp_portal_mobile.feature.reservation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import io.ktor.util.reflect.instanceOf
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.UtcOffset
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.kama_diesel.corp_portal_mobile.resources.Res
import ru.kama_diesel.corp_portal_mobile.resources.apply
import ru.kama_diesel.corp_portal_mobile.resources.check_24px
import ru.kama_diesel.corp_portal_mobile.resources.close
import ru.kama_diesel.corp_portal_mobile.resources.date
import ru.kama_diesel.corp_portal_mobile.resources.date_range_24px
import ru.kama_diesel.corp_portal_mobile.resources.from_to
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
internal fun ReservationFilterPanel(
    office: Office,
    fromDate: Long?,
    toDate: Long?,
    onOfficeChange: (Office) -> Unit,
    onDateChange: (Long?, Long?) -> Unit,
) {
    var sorterExpanded by remember { mutableStateOf(false) }
    var showModal by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.inverseSurface)
            .padding(horizontal = 12.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ExposedDropdownMenuBox(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(0.35f),
            expanded = sorterExpanded,
            onExpandedChange = {
                sorterExpanded = !sorterExpanded
                if (!sorterExpanded) {
                    focusManager.clearFocus()
                }
            }
        ) {
            Row(
                modifier = Modifier
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, true),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 10.dp),
                    text = stringResource(office.shortStringResourceId),
                    fontSize = 12.sp,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.scrim,
                )
                TrailingIcon(expanded = sorterExpanded)
            }

            ExposedDropdownMenu(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.inverseSurface),
                expanded = sorterExpanded,
                matchAnchorWidth = false,
                onDismissRequest = {
                    sorterExpanded = false
                    focusManager.clearFocus()
                }
            ) {
                Office.entries.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = stringResource(item.stringResourceId),
                                color = if (item == office) {
                                    MaterialTheme.colorScheme.inverseOnSurface
                                } else {
                                    MaterialTheme.colorScheme.scrim
                                },
                                fontSize = 12.sp,
                                lineHeight = 14.sp,
                            )
                        },
                        trailingIcon = {
                            if (item == office) {
                                Icon(
                                    modifier = Modifier.size(16.dp),
                                    painter = painterResource(Res.drawable.check_24px),
                                    tint = MaterialTheme.colorScheme.inverseOnSurface,
                                    contentDescription = null,
                                )
                            }
                        },
                        onClick = {
                            sorterExpanded = false
                            focusManager.clearFocus()
                            onOfficeChange(item)
                        },
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .pointerInput(Pair(fromDate, toDate)) {
                    awaitEachGesture {
                        awaitFirstDown(pass = PointerEventPass.Initial)
                        val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                        if (upEvent != null) {
                            showModal = true
                        }
                    }
                },
            singleLine = true,
            value = convertMillisToDateRange(fromDate, toDate),
            onValueChange = { },
            label = {
                Text(
                    text = stringResource(Res.string.date),
                    fontSize = 12.sp,
                )
            },
            textStyle = LocalTextStyle.current.copy(fontSize = 12.sp),
            readOnly = true,
            trailingIcon = {
                Icon(
                    painter = painterResource(Res.drawable.date_range_24px),
                    contentDescription = null,
                )
            },
            colors = OutlinedTextFieldDefaults.colors().copy(
                focusedTextColor = MaterialTheme.colorScheme.primary,
                unfocusedTextColor = MaterialTheme.colorScheme.primary,
            ),
        )
    }

    if (showModal) {
        val dateRangePickerState = rememberDateRangePickerState(
            initialSelectedStartDateMillis = fromDate?.let {
                Instant.fromEpochMilliseconds(it).toLocalDateTime(TimeZone.currentSystemDefault())
                    .toInstant(timeZone = TimeZone.UTC).toEpochMilliseconds()
            },
            initialSelectedEndDateMillis = toDate?.let {
                Instant.fromEpochMilliseconds(it).toLocalDateTime(TimeZone.currentSystemDefault())
                    .toInstant(timeZone = TimeZone.UTC).toEpochMilliseconds()
            },
            selectableDates = FutureOrPresentSelectableDates,
        )
        DatePickerDialog(
            properties = DialogProperties(usePlatformDefaultWidth = false),
            onDismissRequest = { showModal = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDateChange(
                            dateRangePickerState.selectedStartDateMillis,
                            dateRangePickerState.selectedEndDateMillis
                        )
                        showModal = false
                        focusManager.clearFocus()
                    }) {
                    Text(text = stringResource(Res.string.apply))
                }
            },
            dismissButton = {
                TextButton(onClick = { showModal = false }) {
                    Text(
                        text = stringResource(Res.string.close)
                    )
                }
            }
        ) {
            DateRangePicker(
                state = dateRangePickerState,
                headline = null,
                title = null,
                showModeToggle = false,
                colors = DatePickerDefaults.colors(
                    dayContentColor = MaterialTheme.colorScheme.inverseOnSurface,
                    disabledDayContentColor = MaterialTheme.colorScheme.outline,
                    dayInSelectionRangeContainerColor = MaterialTheme.colorScheme.inverseOnSurface.copy(alpha = 0.7f)
                ),
                modifier = Modifier
                    .height(500.dp)
                    .padding(vertical = 12.dp)
            )
        }
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
        if (fromDateString != null && toDateString != null && fromDateString != toDateString) {
            append(stringResource(Res.string.from_to, fromDateString, toDateString))
        } else {
            append(fromDateString ?: toDateString ?: "")
        }
    }
}

@OptIn(ExperimentalTime::class)
object FutureOrPresentSelectableDates : SelectableDates {
    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date <= Instant.fromEpochMilliseconds(utcTimeMillis).toLocalDateTime(TimeZone.currentSystemDefault()).date
    }

    override fun isSelectableYear(year: Int): Boolean {
        return (year - Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).year) >= 0
    }
}
