package ru.kama_diesel.corp_portal_mobile.feature.shop.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.cart.CartScreenContainer
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.ShopListScreenContainer

@Composable
fun ShopFlowScreenComponent(shopFlowComponent: ShopFlowComponent) {
    val childStack by shopFlowComponent.router.childStack.subscribeAsState()

    Children(
        stack = childStack,
        animation = stackAnimation(slide())
    ) {
        when (val child = it.instance) {
            is ShopFlowRouter.Child.ShopList -> {
                ShopListScreenContainer(child.component.viewModel)
            }

            is ShopFlowRouter.Child.Cart -> {
                CartScreenContainer(child.component.viewModel)
            }
        }
    }
}
