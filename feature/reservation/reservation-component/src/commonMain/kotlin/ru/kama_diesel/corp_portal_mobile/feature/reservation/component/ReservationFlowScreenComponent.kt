package ru.kama_diesel.corp_portal_mobile.feature.reservation.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import ru.kama_diesel.corp_portal_mobile.feature.reservation.ui.screen.ReservationScreenContainer

@Composable
fun ReservationFlowScreenComponent(
    reservationFlowComponent: ReservationFlowComponent,
) {
    val childStack by reservationFlowComponent.router.childStack.subscribeAsState()

    Children(
        stack = childStack,
        animation = stackAnimation(slide())
    ) {
        when (val child = it.instance) {
            is ReservationFlowRouter.Child.Top -> {
                ReservationScreenContainer(
                    viewModel = child.component.viewModel,
                )
            }
        }
    }
}
