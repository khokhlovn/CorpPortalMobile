package ru.kama_diesel.corp_portal_mobile.common.data.network.mapper

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object DateTimeMapper {

    fun getFormattedDate(
        iso8601Timestamp: String,
        format: String,
    ): String
}
