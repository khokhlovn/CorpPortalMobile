package ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.component.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import ru.kama_diesel.corp_portal_mobile.common.component.registerAndGetSavedState
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.component.di.PhoneDirectoryFlowDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.component.list.di.PhoneDirectoryListDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.component.list.di.create
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.ui.screen.list.Direction
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.ui.screen.list.Sorter
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.ui.screen.list.model.PhoneDirectoryListDialog
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.ui.screen.list.model.PhoneDirectoryListViewState

class PhoneDirectoryListComponent(
    componentContext: ComponentContext,
    phoneDirectoryFlowDIComponent: PhoneDirectoryFlowDIComponent,
) : ComponentContext by componentContext {

    private val savedState: PhoneDirectoryListViewState = registerAndGetSavedState(
        key = PHONE_DIRECTORY_LIST_SAVED_STATE,
        initialValue = PhoneDirectoryListViewState(
            employeeItems = listOf(),
            filteredEmployeeItems = listOf(),
            selectedSorter = Sorter.Name,
            selectedDirection = Direction.Increasing,
            query = "",
            dialog = PhoneDirectoryListDialog.No,
            isLoading = true,
        ),
        deserialization = PhoneDirectoryListViewState.serializer(),
        serialization = PhoneDirectoryListViewState.serializer()
    ) {
        viewModel.currentState
    }

    private val diComponent = instanceKeeper.getOrCreate {
        PhoneDirectoryListDIComponent::class.create(phoneDirectoryFlowDIComponent, savedState)
    }

    val viewModel = diComponent.viewModel

    companion object Companion {
        private const val PHONE_DIRECTORY_LIST_SAVED_STATE = "PHONE_DIRECTORY_LIST_SAVED_STATE"
    }
}
