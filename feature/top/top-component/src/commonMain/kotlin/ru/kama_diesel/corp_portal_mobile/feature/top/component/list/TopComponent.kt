package ru.kama_diesel.corp_portal_mobile.feature.top.component.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import ru.kama_diesel.corp_portal_mobile.common.component.registerAndGetSavedState
import ru.kama_diesel.corp_portal_mobile.feature.top.component.di.TopFlowDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.top.component.list.di.TopDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.top.component.list.di.create
import ru.kama_diesel.corp_portal_mobile.feature.top.ui.screen.model.TopViewState

class TopComponent(
    componentContext: ComponentContext,
    topFlowDIComponent: TopFlowDIComponent,
) : ComponentContext by componentContext {

    private val savedState: TopViewState = registerAndGetSavedState(
        key = TOP_SAVED_STATE,
        initialValue = TopViewState(
            topWorkers = listOf(),
            isLoading = true,
        ),
        deserialization = TopViewState.serializer(),
        serialization = TopViewState.serializer()
    ) {
        viewModel.currentState
    }

    private val diComponent = instanceKeeper.getOrCreate {
        TopDIComponent::class.create(topFlowDIComponent, savedState)
    }

    val viewModel = diComponent.viewModel

    companion object Companion {
        private const val TOP_SAVED_STATE = "TOP_SAVED_STATE"
    }
}
