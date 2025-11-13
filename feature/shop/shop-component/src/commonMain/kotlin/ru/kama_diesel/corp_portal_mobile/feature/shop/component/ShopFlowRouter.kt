package ru.kama_diesel.corp_portal_mobile.feature.shop.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import ru.kama_diesel.corp_portal_mobile.feature.shop.component.di.ShopFlowDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.shop.component.list.ShopListComponent
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.api.IShopFlowRouter

class ShopFlowRouter(
    componentContext: ComponentContext,
    private val shopFlowDIComponent: ShopFlowDIComponent
) : IShopFlowRouter {

    private val stackNavigation = StackNavigation<Configuration>()

    internal val childStack: Value<ChildStack<*, Child>> = componentContext.childStack(
        source = stackNavigation,
        serializer = Configuration.serializer(),
        handleBackButton = true,
        childFactory = ::childFactory,
        initialStack = { listOf(Configuration.ShopList) }
    )

    private fun childFactory(config: Configuration, componentContext: ComponentContext): Child {
        return when (config) {
            is Configuration.ShopList -> Child.ShopList(
                component = ShopListComponent(
                    componentContext = componentContext,
                    shopFlowDIComponent = shopFlowDIComponent
                )
            )
        }
    }

    internal sealed interface Child {
        class ShopList(val component: ShopListComponent) : Child
    }

    @Serializable
    internal sealed class Configuration {
        @Serializable
        data object ShopList : Configuration()
    }

    override fun back() {
        stackNavigation.pop()
    }
}