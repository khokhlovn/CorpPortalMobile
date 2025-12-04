package ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.ui.screen.list.PhoneDirectoryListScreenContainer

@Composable
fun PhoneDirectoryFlowScreenComponent(
    shopFlowComponent: PhoneDirectoryFlowComponent
) {
    val childStack by shopFlowComponent.router.childStack.subscribeAsState()

    Children(
        stack = childStack,
        animation = stackAnimation(slide())
    ) {
        when (val child = it.instance) {
            is PhoneDirectoryFlowRouter.Child.PhoneDirectoryList -> {
                PhoneDirectoryListScreenContainer(
                    viewModel = child.component.viewModel,
                )
            }
        }
    }
}
