package ru.kama_diesel.corp_portal_mobile.common.data.network.mapper

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object DateTimeMapper {

    actual fun getFormattedDate(
        iso8601Timestamp: String,
        format: String,
    ): String {
        val date = getDateFromIso8601Timestamp(iso8601Timestamp)
        val formatter = DateTimeFormatter.ofPattern(format)
        return date.format(formatter)
    }

    private fun getDateFromIso8601Timestamp(string: String): ZonedDateTime {
        return ZonedDateTime.parse(string)
    }

    @OptIn(ExperimentalTime::class, FormatStringsInDatetimeFormats::class)
    actual fun getFormattedDate(millis: Long, format: String): String {
        return Instant.fromEpochMilliseconds(epochMilliseconds = millis)
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .format(
                format = LocalDateTime.Format {
                    byUnicodePattern(pattern = format)
                }
            )
    }
}
