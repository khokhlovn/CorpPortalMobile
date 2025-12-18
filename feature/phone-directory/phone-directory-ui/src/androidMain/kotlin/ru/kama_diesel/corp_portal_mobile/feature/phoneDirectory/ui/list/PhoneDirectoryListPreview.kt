package ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.ui.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.kama_diesel.corp_portal_mobile.common.ui.theme.AppTheme
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.ui.screen.list.Direction
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.ui.screen.list.PhoneDirectoryListScreen
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.ui.screen.list.PhoneDirectoryListScreenContent
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.ui.screen.list.Sorter
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.ui.screen.list.model.EmployeeItemUIModel
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.ui.screen.list.model.PhoneDirectoryListDialog
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.ui.screen.list.model.PhoneDirectoryListViewState

@Preview
@Composable
private fun PhoneDirectoryListScreenPreview() {
    AppTheme {
        PhoneDirectoryListScreen(
            viewState = PhoneDirectoryListViewState(
                employeeItems = listOf(),
                filteredEmployeeItems = listOf(),
                selectedSorter = Sorter.Name,
                selectedDirection = Direction.Increasing,
                query = "",
                dialog = PhoneDirectoryListDialog.No,
                isLoading = false,
            ),
            onQueryChange = {},
            onQueryClear = {},
            onRefresh = {},
            onCloseDialogClick = {},
            onPinChange = { _, _ -> },
            onSorterChange = {},
            onDirectionChange = {},
            onEmployeeItemClick = {},
        )
    }
}

@Preview
@Composable
private fun PhoneDirectoryListScreenContentPreview() {
    AppTheme {
        PhoneDirectoryListScreenContent(
            filteredEmployeeItems = employeeItemsPreviewData,
            allEmployeeItemsCount = 2,
            selectedSorter = Sorter.Name,
            selectedDirection = Direction.Increasing,
            isRefreshing = false,
            query = "",
            onRefresh = {},
            onQueryChange = {},
            onQueryClear = {},
            onPinChange = { _, _ -> },
            onSorterChange = {},
            onDirectionChange = {},
            onEmployeeItemClick = {},
        )
    }
}

private val employeeItemsPreviewData = listOf(
    EmployeeItemUIModel(
        fullName = "Иванов Иван Иванович",
        imagePath = null,
        position = "Старший мастер",
        department = "Цех обработки блока цилиндров",
        service = "Вспомогательные рабочие",
        mail = "ivanov.ivan.ivanovich@mail.ru",
        mobile = "9211231234",
        phone = null,
        workPlace = "Л-126",
        isPinned = true,
    ),
    EmployeeItemUIModel(
        fullName = "Петров Петр Петрович",
        imagePath = null,
        position = "Главный специалист по пожарной безопасности, делам гражданской обороны и чрезвычайным ситуациям",
        department = "Группа по обеспечению деятельности в области охраны труда, пожарной безопасности и охраны окружающей среды",
        service = "РСиС, руководитель ВЗУ",
        mail = "petrov.petr@mail.ru",
        mobile = null,
        phone = "99-88-77",
        workPlace = null,
        isPinned = false,
    ),
    EmployeeItemUIModel(
        fullName = "Петров Петр Петрович",
        imagePath = null,
        position = "Главный специалист по пожарной безопасности, делам гражданской обороны и чрезвычайным ситуациям",
        department = "Группа по обеспечению деятельности в области охраны труда, пожарной безопасности и охраны окружающей среды",
        service = "РСиС, руководитель ВЗУ",
        mail = "petrov.petr@mail.ru",
        mobile = null,
        phone = "99-88-77",
        workPlace = "А-123",
        isPinned = false,
    ),
)
