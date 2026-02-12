package ru.kama_diesel.corp_portal_mobile.feature.profile.component.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import ru.kama_diesel.corp_portal_mobile.common.component.registerAndGetSavedState
import ru.kama_diesel.corp_portal_mobile.feature.profile.component.di.ProfileFlowDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.profile.component.list.di.BalanceDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.profile.component.list.di.create
import ru.kama_diesel.corp_portal_mobile.feature.profile.ui.screen.balance.Sorter
import ru.kama_diesel.corp_portal_mobile.feature.profile.ui.screen.balance.model.BalanceViewState

class BalanceComponent(
    componentContext: ComponentContext,
    profileFlowDIComponent: ProfileFlowDIComponent,
) : ComponentContext by componentContext {

    private val savedState: BalanceViewState = registerAndGetSavedState(
        key = BALANCE_SAVED_STATE,
        initialValue = BalanceViewState(
            balance = 0,
            giftBalance = 0,
            weeklyAward = 0,
            endOfWeekDate = "",
            fullName = "",
            historyEvents = listOf(),
            filteredHistoryEvents = listOf(),
            selectedSorter = Sorter.DateDecreasing,
            query = "",
            isLoading = true,
            showSuccessSnackbar = false,
            showErrorSnackbar = false,
        ),
        deserialization = BalanceViewState.serializer(),
        serialization = BalanceViewState.serializer()
    ) {
        viewModel.currentState
    }

    private val diComponent = instanceKeeper.getOrCreate {
        BalanceDIComponent::class.create(profileFlowDIComponent, savedState)
    }

    val viewModel = diComponent.viewModel

    companion object Companion {
        private const val BALANCE_SAVED_STATE = "BALANCE_SAVED_STATE"
    }
}
