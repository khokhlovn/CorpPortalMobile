package ru.kama_diesel.corp_portal_mobile.feature.profile.ui.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.kama_diesel.corp_portal_mobile.common.ui.theme.AppTheme
import ru.kama_diesel.corp_portal_mobile.feature.profile.ui.screen.balance.BalanceScreen
import ru.kama_diesel.corp_portal_mobile.feature.profile.ui.screen.balance.Sorter
import ru.kama_diesel.corp_portal_mobile.feature.profile.ui.screen.balance.model.BalanceViewState

@Preview
@Composable
private fun BalanceScreenPreview() {
    AppTheme {
        BalanceScreen(
            viewState = BalanceViewState(
                balance = 12261,
                giftBalance = 10,
                weeklyAward = 2,
                endOfWeekDate = "12.12.2026",
                fullName = "",
                historyEvents = listOf(),
                filteredHistoryEvents = listOf(),
                selectedSorter = Sorter.DateDecreasing,
                query = "",
                isLoading = false,
                showSuccessSnackbar = false,
                showErrorSnackbar = false,
            ),
            onRefresh = {},
            onBackClick = {},
            onQueryChange = {},
            onQueryClear = {},
            onSorterChange = {},
            onHideSnackbar = {},
            onToTransferClick = {},
            onToBalanceClick = {},
            toShop = {},
        )
    }
}
