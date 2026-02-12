package ru.kama_diesel.corp_portal_mobile.feature.profile.ui.screen.balance.model

import kotlinx.serialization.Serializable
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ThxHistoryItem
import ru.kama_diesel.corp_portal_mobile.feature.profile.ui.screen.balance.Sorter

@Serializable
data class BalanceViewState(
    val fullName: String,
    val balance: Int,
    val giftBalance: Int,
    val weeklyAward: Int,
    val endOfWeekDate: String,
    val historyEvents: List<ThxHistoryItem>,
    val selectedSorter: Sorter,
    val query: String,
    val filteredHistoryEvents: List<ThxHistoryItem>,
    val isLoading: Boolean,
    val showSuccessSnackbar: Boolean,
    val showErrorSnackbar: Boolean,
)
