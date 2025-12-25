package ru.kama_diesel.corp_portal_mobile.feature.shop.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import ru.kama_diesel.corp_portal_mobile.feature.shop.component.di.ShopFlowDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.shop.component.list.CartComponent
import ru.kama_diesel.corp_portal_mobile.feature.shop.component.list.OrdersComponent
import ru.kama_diesel.corp_portal_mobile.feature.shop.component.list.ShopListComponent
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.api.IShopFlowRouter

@OptIn(DelicateDecomposeApi::class)
class ShopFlowRouter(
    componentContext: ComponentContext,
    private val initialConfiguration: Configuration,
    private val shopFlowDIComponent: ShopFlowDIComponent
) : IShopFlowRouter {

    private val stackNavigation = StackNavigation<Configuration>()

    internal val childStack: Value<ChildStack<*, Child>> = componentContext.childStack(
        source = stackNavigation,
        serializer = Configuration.serializer(),
        handleBackButton = true,
        childFactory = ::childFactory,
        initialStack = { listOf(initialConfiguration) }
    )

    private fun childFactory(config: Configuration, componentContext: ComponentContext): Child {
        return when (config) {
            is Configuration.ShopList -> Child.ShopList(
                component = ShopListComponent(
                    componentContext = componentContext,
                    shopFlowDIComponent = shopFlowDIComponent
                )
            )

            is Configuration.Cart -> Child.Cart(
                component = CartComponent(
                    componentContext = componentContext,
                    shopFlowDIComponent = shopFlowDIComponent
                )
            )

            is Configuration.Orders -> Child.Orders(
                component = OrdersComponent(
                    componentContext = componentContext,
                    shopFlowDIComponent = shopFlowDIComponent
                )
            )
        }
    }

    internal sealed interface Child {
        class ShopList(val component: ShopListComponent) : Child
        class Cart(val component: CartComponent) : Child
        class Orders(val component: OrdersComponent) : Child
    }

    @Serializable
    sealed class Configuration {
        @Serializable
        data object ShopList : Configuration()

        @Serializable
        data object Cart : Configuration()

        @Serializable
        data object Orders : Configuration()
    }

    override fun toCart() {
        stackNavigation.push(Configuration.Cart)
    }

    override fun toOrders() {
        stackNavigation.push(Configuration.Orders)
    }

    override fun back() {
        stackNavigation.pop()
        (childStack.active.instance as Child.ShopList).component.viewModel.getData()
    }
}