package ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.ui.screen.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun PhoneDirectoryListScreenContainer(
    viewModel: PhoneDirectoryListViewModel,
) {

    val viewState by viewModel.viewState.collectAsState()

    PhoneDirectoryListScreen(
        viewState = viewState,
        onQueryChange = viewModel::onQueryChange,
        onQueryClear = viewModel::onQueryClear,
        onRefresh = viewModel::getData,
        onCloseDialogClick = viewModel::onCloseDialogClick,
        onPinChange = viewModel::onPinChange,
        onSorterChange = viewModel::onSorterChange,
        onDirectionChange = viewModel::onDirectionChange,
    )
}
