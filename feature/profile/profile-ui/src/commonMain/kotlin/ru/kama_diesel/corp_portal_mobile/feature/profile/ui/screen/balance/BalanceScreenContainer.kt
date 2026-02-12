package ru.kama_diesel.corp_portal_mobile.feature.profile.ui.screen.balance

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun BalanceScreenContainer(
    viewModel: BalanceViewModel,
) {
    val viewState by viewModel.viewState.collectAsState()

    BalanceScreen(
        viewState = viewState,
        onBackClick = viewModel::back,
        onRefresh = viewModel::getData,
        onQueryChange = viewModel::onQueryChange,
        onQueryClear = viewModel::onQueryClear,
        onSorterChange = viewModel::onSorterChange,
        onHideSnackbar = viewModel::onHideSnackbar,
        onToTransferClick = viewModel::onToTransferClick,
        onToBalanceClick = viewModel::onToBalanceClick,
    )
}
