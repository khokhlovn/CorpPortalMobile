package ru.kama_diesel.corp_portal_mobile.feature.reservation.component.list.di

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import ru.kama_diesel.corp_portal_mobile.feature.reservation.component.di.ReservationFlowDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.reservation.domain.di.ReservationScope
import ru.kama_diesel.corp_portal_mobile.feature.reservation.ui.screen.ReservationViewModel
import ru.kama_diesel.corp_portal_mobile.feature.reservation.ui.screen.model.ReservationViewState

@ReservationScope
@Component
abstract class ReservationDIComponent(
    @Component val parent: ReservationFlowDIComponent,
    private val initialState: ReservationViewState,
) : InstanceKeeper.Instance {

    abstract val viewModel: ReservationViewModel

    @Provides
    protected fun getInitialState(): ReservationViewState = initialState

    override fun onDestroy() {
        viewModel.onDestroy()
    }
}
