package ru.kama_diesel.corp_portal_mobile.common.data.network.mapper

import platform.Foundation.*

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object DateTimeMapper {
    actual fun getFormattedDate(
        iso8601Timestamp: String,
        format: String,
    ): String {
        val date = getDateFromIso8601Timestamp(iso8601Timestamp) ?: return ""

        val dateFormatter = NSDateFormatter()
        dateFormatter.timeZone = NSTimeZone.localTimeZone
        dateFormatter.locale = NSLocale.autoupdatingCurrentLocale
        dateFormatter.dateFormat = format
        return dateFormatter.stringFromDate(date)
    }

    private fun getDateFromIso8601Timestamp(string: String): NSDate? {
        return NSISO8601DateFormatter().dateFromString(string)
    }
}
