package ru.kama_diesel.corp_portal_mobile.feature.profile.component.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import ru.kama_diesel.corp_portal_mobile.common.component.registerAndGetSavedState
import ru.kama_diesel.corp_portal_mobile.feature.profile.component.di.ProfileFlowDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.profile.component.list.di.TransferDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.profile.component.list.di.create
import ru.kama_diesel.corp_portal_mobile.feature.profile.ui.screen.transfer.model.TransferViewState

class TransferComponent(
    componentContext: ComponentContext,
    profileFlowDIComponent: ProfileFlowDIComponent,
) : ComponentContext by componentContext {

    private val savedState: TransferViewState = registerAndGetSavedState(
        key = TRANSFER_SAVED_STATE,
        initialValue = TransferViewState(
            amount = null,
            availableAmount = 0,
            selectedUserId = null,
            role = 999,
            userName = "",
            userIdsWithNames = listOf(),
            filteredUserIdsWithNames = listOf(),
            isLoading = true,
            showSuccessSnackbar = false,
            error = "",
        ),
        deserialization = TransferViewState.serializer(),
        serialization = TransferViewState.serializer()
    ) {
        viewModel.currentState
    }

    private val diComponent = instanceKeeper.getOrCreate {
        TransferDIComponent::class.create(profileFlowDIComponent, savedState)
    }

    val viewModel = diComponent.viewModel

    companion object Companion {
        private const val TRANSFER_SAVED_STATE = "TRANSFER_SAVED_STATE"
    }
}
