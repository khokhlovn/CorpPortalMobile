package ru.kama_diesel.corp_portal_mobile.feature.top.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import ru.kama_diesel.corp_portal_mobile.feature.top.component.di.TopFlowDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.top.component.list.TopComponent
import ru.kama_diesel.corp_portal_mobile.feature.top.ui.api.ITopFlowRouter

@OptIn(DelicateDecomposeApi::class)
class TopFlowRouter(
    componentContext: ComponentContext,
    private val topFlowDIComponent: TopFlowDIComponent
) : ITopFlowRouter {

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
                component = TopComponent(
                    componentContext = componentContext,
                    topFlowDIComponent = topFlowDIComponent,
                )
            )
        }
    }

    internal sealed interface Child {
        class Top(val component: TopComponent) : Child
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