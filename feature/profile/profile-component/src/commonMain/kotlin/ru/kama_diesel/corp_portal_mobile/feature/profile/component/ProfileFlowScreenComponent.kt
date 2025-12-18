package ru.kama_diesel.corp_portal_mobile.feature.profile.component

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import ru.kama_diesel.corp_portal_mobile.feature.profile.ui.screen.ProfileScreenContainer

@Composable
fun ProfileFlowScreenComponent(
    drawerState: DrawerState,
    profileFlowComponent: ProfileFlowComponent
) {
    val childStack by profileFlowComponent.router.childStack.subscribeAsState()

    Children(
        stack = childStack,
        animation = stackAnimation(slide())
    ) {
        when (val child = it.instance) {
            is ProfileFlowRouter.Child.Profile -> {
                ProfileScreenContainer(
                    viewModel = child.component.viewModel,
                    drawerState = drawerState,
                )
            }
        }
    }
}
