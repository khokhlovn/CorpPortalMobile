package ru.kama_diesel.corp_portal_mobile.feature.reservation.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import ru.kama_diesel.corp_portal_mobile.feature.reservation.component.api.IReservationComponentDependencies
import ru.kama_diesel.corp_portal_mobile.feature.reservation.component.di.ReservationFlowDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.reservation.component.di.create

class ReservationFlowComponent(
    componentContext: ComponentContext,
    componentDependencies: IReservationComponentDependencies
) : ComponentContext by componentContext {

    private val diComponent = instanceKeeper.getOrCreate {
        ReservationFlowDIComponent::class.create(componentDependencies)
    }

    val router = ReservationFlowRouter(componentContext, diComponent)

    init {
        diComponent.getRouterHolder().updateInstance(router)
    }
}
