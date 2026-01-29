package ru.kama_diesel.corp_portal_mobile.feature.reservation.component.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.atTime
import kotlinx.datetime.toInstant
import kotlinx.datetime.todayIn
import ru.kama_diesel.corp_portal_mobile.common.component.registerAndGetSavedState
import ru.kama_diesel.corp_portal_mobile.feature.reservation.component.di.ReservationFlowDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.reservation.component.list.di.ReservationDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.reservation.component.list.di.create
import ru.kama_diesel.corp_portal_mobile.feature.reservation.ui.screen.Office
import ru.kama_diesel.corp_portal_mobile.feature.reservation.ui.screen.model.ReservationDialog
import ru.kama_diesel.corp_portal_mobile.feature.reservation.ui.screen.model.ReservationViewState
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class ReservationComponent(
    componentContext: ComponentContext,
    reservationFlowDIComponent: ReservationFlowDIComponent,
) : ComponentContext by componentContext {

    @OptIn(ExperimentalTime::class)
    private val savedState: ReservationViewState = registerAndGetSavedState(
        key = RESERVATION_SAVED_STATE,
        initialValue = ReservationViewState(
            reservationItems = listOf(),
            office = Office.FirstLeft,
            fromDate = Clock.System.todayIn(TimeZone.currentSystemDefault()).atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds(),
            toDate = Clock.System.todayIn(TimeZone.currentSystemDefault()).atTime(23, 59, 59).toInstant(TimeZone.currentSystemDefault())
                .toEpochMilliseconds(),
            dialog = ReservationDialog.No,
            total = 0,
            free = 0,
            reserved = 0,
            unavailable = 0,
            selectedPlace = null,
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
