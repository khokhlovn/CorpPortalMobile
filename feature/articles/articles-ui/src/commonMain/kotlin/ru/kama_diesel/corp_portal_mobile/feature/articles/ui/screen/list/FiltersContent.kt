package ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import kotlinx.datetime.*
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.model.TagItemUIModel
import ru.kama_diesel.corp_portal_mobile.resources.*
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltersContent(
    modifier: Modifier = Modifier,
    tagItems: List<TagItemUIModel>,
    fromDate: Long?,
    toDate: Long?,
    onCheckedChange: (String, Boolean) -> Unit,
    onDateChange: (Long?, Long?) -> Unit,
    onResetFilters: () -> Unit,
    onApplyFilters: () -> Unit,
    onHideFilters: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .requiredWidth(260.dp)
            .background(color = MaterialTheme.colorScheme.inverseSurface)
            .animateContentSize(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        var showModal by remember { mutableStateOf(false) }

        Text(
            modifier = Modifier.padding(top = 12.dp, start = 12.dp),
            text = stringResource(Res.string.filters),
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(height = 12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                modifier = Modifier.padding(start = 12.dp),
                fontSize = 16.sp,
                text = stringResource(Res.string.tags)
            )
            Spacer(modifier = Modifier.height(height = 8.dp))
            LazyColumn(
                modifier = Modifier.fillMaxWidth().heightIn(max = 400.dp),
                contentPadding = PaddingValues(end = 12.dp),
            ) {
                items(items = tagItems) { tagItem ->
                    TagItemContent(
                        item = tagItem,
                        onCheckedChange = onCheckedChange,
                    )
                }
            }
            Spacer(modifier = Modifier.height(height = 12.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp)
                    .pointerInput(Pair(fromDate, toDate)) {
                        awaitEachGesture {
                            awaitFirstDown(pass = PointerEventPass.Initial)
                            val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                            if (upEvent != null) {
                                showModal = true
                            }
                        }
                    },
                value = convertMillisToDateRange(fromDate, toDate),
                onValueChange = { },
                label = {
                    Text(
                        text = stringResource(Res.string.date),
                    )
                },
                textStyle = TextStyle(fontSize = 14.sp),
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

            Spacer(modifier = Modifier.height(height = 12.dp))
            Button(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                enabled = tagItems.any { it.isChecked } || fromDate != null || toDate != null,
                shape = ShapeDefaults.Medium,
                onClick = onResetFilters,
            ) {
                Text(text = stringResource(Res.string.reset))
            }
            Button(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                shape = ShapeDefaults.Medium,
                onClick = {
                    onApplyFilters()
                    onHideFilters()
                },
            ) {
                Text(text = stringResource(Res.string.search))
            }
            Spacer(modifier = Modifier.height(height = 12.dp))
        }

        if (showModal) {
            val dateRangePickerState = rememberDateRangePickerState(
                initialSelectedStartDateMillis = fromDate,
                initialSelectedEndDateMillis = toDate,
                selectableDates = PastOrPresentSelectableDates,
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
}

@Composable
private fun TagItemContent(
    item: TagItemUIModel,
    onCheckedChange: (String, Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .clickable { onCheckedChange(item.tagItem.id, !item.isChecked) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val backgroundColor = item.tagItem.backgroundColor?.toColor() ?: MaterialTheme.colorScheme.primaryContainer
        val textColor = item.tagItem.textColor?.toColor() ?: MaterialTheme.colorScheme.onPrimaryContainer

        Checkbox(
            checked = item.isChecked,
            colors = CheckboxDefaults.colors().copy(
                checkedBoxColor = backgroundColor,
                checkedBorderColor = backgroundColor,
                checkedCheckmarkColor = textColor,
            ),
            onCheckedChange = { isChecked ->
                onCheckedChange(item.tagItem.id, isChecked)
            },
        )

        Text(
            modifier = Modifier
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(size = 8.dp)
                )
                .padding(horizontal = 8.dp, vertical = 4.dp),
            text = item.tagItem.name,
            color = textColor,
            fontSize = 12.sp,
            maxLines = 1,
        )
    }
}

private fun String.toColor(): Color {
    val clean = this.trim().removePrefix("#").uppercase()

    return when (clean.length) {
        3 -> {
            // RGB -> RRGGBB
            val r = clean[0].digitToInt(16) * 17
            val g = clean[1].digitToInt(16) * 17
            val b = clean[2].digitToInt(16) * 17
            Color(red = r, green = g, blue = b, alpha = 255)
        }

        4 -> {
            // ARGB
            val a = clean[0].digitToInt(radix = 16) * 17
            val r = clean[1].digitToInt(16) * 17
            val g = clean[2].digitToInt(16) * 17
            val b = clean[3].digitToInt(16) * 17
            Color(alpha = a, red = r, green = g, blue = b)
        }

        6 -> {
            // RRGGBB
            val r = clean.substring(0, 2).toInt(16)
            val g = clean.substring(2, 4).toInt(16)
            val b = clean.substring(4, 6).toInt(16)
            Color(red = r, green = g, blue = b, alpha = 255)
        }

        8 -> {
            // AARRGGBB
            val a = clean.substring(0, 2).toInt(16)
            val r = clean.substring(2, 4).toInt(16)
            val g = clean.substring(4, 6).toInt(16)
            val b = clean.substring(6, 8).toInt(16)
            Color(alpha = a, red = r, green = g, blue = b)
        }

        else -> throw IllegalArgumentException("Invalid Hex color: $this")
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
        if (fromDateString != null && toDateString != null) {
            append(stringResource(Res.string.from_to, fromDateString, toDateString))
        } else {
            append(fromDateString ?: toDateString ?: "")
        }
    }
}

@OptIn(ExperimentalTime::class)
object PastOrPresentSelectableDates : SelectableDates {
    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        return utcTimeMillis <= Clock.System.now().plus(DateTimePeriod(days = 1), TimeZone.currentSystemDefault())
            .toEpochMilliseconds()
    }

    override fun isSelectableYear(year: Int): Boolean {
        return year <= Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).year
    }
}
