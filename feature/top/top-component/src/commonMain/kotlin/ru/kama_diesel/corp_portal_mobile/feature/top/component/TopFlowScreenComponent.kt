package ru.kama_diesel.corp_portal_mobile.feature.top.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import ru.kama_diesel.corp_portal_mobile.feature.top.ui.screen.TopScreenContainer

@Composable
fun TopFlowScreenComponent(
    topFlowComponent: TopFlowComponent,
) {
    val childStack by topFlowComponent.router.childStack.subscribeAsState()

    Children(
        stack = childStack,
        animation = stackAnimation(slide())
    ) {
        when (val child = it.instance) {
            is TopFlowRouter.Child.Top -> {
                TopScreenContainer(
                    viewModel = child.component.viewModel,
                )
            }
        }
    }
}
