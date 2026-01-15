package ru.kama_diesel.corp_portal_mobile.feature.reservation.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import ru.kama_diesel.corp_portal_mobile.feature.reservation.component.di.ReservationFlowDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.reservation.component.list.ReservationComponent
import ru.kama_diesel.corp_portal_mobile.feature.reservation.ui.api.IReservationFlowRouter

@OptIn(DelicateDecomposeApi::class)
class ReservationFlowRouter(
    componentContext: ComponentContext,
    private val reservationFlowDIComponent: ReservationFlowDIComponent
) : IReservationFlowRouter {

    private val stackNavigation = StackNavigation<Configuration>()

    internal val childStack: Value<ChildStack<*, Child>> = componentContext.childStack(
        source = stackNavigation,
        serializer = Configuration.serializer(),
        handleBackButton = true,
        childFactory = ::childFactory,
        initialStack = { listOf(Configuration.Profile) }
    )

    private fun childFactory(config: Configuration, componentContext: ComponentContext): Child {
        return when (config) {
            is Configuration.Profile -> Child.Top(
                component = ReservationComponent(
                    componentContext = componentContext,
                    reservationFlowDIComponent = reservationFlowDIComponent,
                )
            )
        }
    }

    internal sealed interface Child {
        class Top(val component: ReservationComponent) : Child
    }

    @Serializable
    internal sealed class Configuration {
        @Serializable
        data object Profile : Configuration()
    }

    override fun back() {
        stackNavigation.pop()
        (childStack.active.instance as Child.Top).component.viewModel.getData()
    }
}