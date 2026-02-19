package ru.kama_diesel.corp_portal_mobile.feature.profile.ui.screen.transfer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun TransferScreenContainer(
    viewModel: TransferViewModel,
) {
    val viewState by viewModel.viewState.collectAsState()

    TransferScreen(
        viewState = viewState,
        onBackClick = viewModel::back,
        onRefresh = viewModel::getData,
        onUserNameChange = viewModel::onUserNameChange,
        onUserSelect = viewModel::onUserSelect,
        onAmountSelect = viewModel::onAmountSelect,
        onAmountSelectCeo = viewModel::onAmountSelectCeo,
        onTransferClick = viewModel::onTransferClick,
        onTransferCeoClick = viewModel::onTransferCeoClick,
        onHideSnackbar = viewModel::onHideSnackbar,
    )
}
