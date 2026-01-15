package ru.kama_diesel.corp_portal_mobile.feature.reservation.component.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import ru.kama_diesel.corp_portal_mobile.common.component.registerAndGetSavedState
import ru.kama_diesel.corp_portal_mobile.feature.reservation.component.di.ReservationFlowDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.reservation.component.list.di.ReservationDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.reservation.component.list.di.create
import ru.kama_diesel.corp_portal_mobile.feature.reservation.ui.screen.model.ReservationViewState

class ReservationComponent(
    componentContext: ComponentContext,
    reservationFlowDIComponent: ReservationFlowDIComponent,
) : ComponentContext by componentContext {

    private val savedState: ReservationViewState = registerAndGetSavedState(
        key = RESERVATION_SAVED_STATE,
        initialValue = ReservationViewState(
            topWorkers = listOf(),
            isLoading = true,
        ),
        deserialization = ReservationViewState.serializer(),
        serialization = ReservationViewState.serializer()
    ) {
        viewModel.currentState
    }

    private val diComponent = instanceKeeper.getOrCreate {
        ReservationDIComponent::class.create(reservationFlowDIComponent, savedState)
    }

    val viewModel = diComponent.viewModel

    companion object Companion {
        private const val RESERVATION_SAVED_STATE = "RESERVATION_SAVED_STATE"
    }
}
