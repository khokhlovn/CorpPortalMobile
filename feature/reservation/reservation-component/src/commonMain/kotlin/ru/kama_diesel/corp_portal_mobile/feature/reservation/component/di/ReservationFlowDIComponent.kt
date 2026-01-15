package ru.kama_diesel.corp_portal_mobile.feature.reservation.component.di

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IReservationRepository
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.ITopRepository
import ru.kama_diesel.corp_portal_mobile.common.ui.navigation.RouterHolder
import ru.kama_diesel.corp_portal_mobile.feature.reservation.component.api.IReservationComponentDependencies
import ru.kama_diesel.corp_portal_mobile.feature.reservation.data.repository.ReservationRepository
import ru.kama_diesel.corp_portal_mobile.feature.reservation.ui.api.IReservationFlowRouter

@Component
abstract class ReservationFlowDIComponent(
    @Component val dependencies: IReservationComponentDependencies
) : InstanceKeeper.Instance {

    private val routerHolder = RouterHolder<IReservationFlowRouter>()

    @Provides
    fun getRouterHolder(): RouterHolder<IReservationFlowRouter> = routerHolder

    val ReservationRepository.bind: IReservationRepository
        @Provides get() = this
}
