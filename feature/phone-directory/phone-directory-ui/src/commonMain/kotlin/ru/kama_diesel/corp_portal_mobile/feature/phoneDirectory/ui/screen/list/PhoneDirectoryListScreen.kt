package ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.ui.screen.list

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.ui.screen.details.PhoneDirectoryItemDetailsDialog
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.ui.screen.list.model.EmployeeItemUIModel
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.ui.screen.list.model.PhoneDirectoryListDialog
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.ui.screen.list.model.PhoneDirectoryListViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneDirectoryListScreen(
    viewState: PhoneDirectoryListViewState,
    onRefresh: () -> Unit,
    onQueryChange: (String) -> Unit,
    onQueryClear: () -> Unit,
    onPinChange: (String, Boolean) -> Unit,
    onSorterChange: (Sorter) -> Unit,
    onDirectionChange: (Direction) -> Unit,
    onCloseDialogClick: () -> Unit,
    onEmployeeItemClick: (EmployeeItemUIModel) -> Unit,
) {
    PhoneDirectoryListScreenContent(
        filteredEmployeeItems = viewState.filteredEmployeeItems,
        allEmployeeItemsCount = viewState.employeeItems.size,
        selectedSorter = viewState.selectedSorter,
        selectedDirection = viewState.selectedDirection,
        query = viewState.query,
        isRefreshing = viewState.isLoading,
        onRefresh = onRefresh,
        onQueryChange = onQueryChange,
        onQueryClear = onQueryClear,
        onPinChange = onPinChange,
        onSorterChange = onSorterChange,
        onDirectionChange = onDirectionChange,
        onEmployeeItemClick = onEmployeeItemClick,
    )

    when (val dialog = viewState.dialog) {
        is PhoneDirectoryListDialog.Details -> PhoneDirectoryItemDetailsDialog(
            employeeItemUIModel = dialog.employeeItemUIModel,
            onCloseClick = onCloseDialogClick,
        )

        PhoneDirectoryListDialog.No -> Unit
    }
}
