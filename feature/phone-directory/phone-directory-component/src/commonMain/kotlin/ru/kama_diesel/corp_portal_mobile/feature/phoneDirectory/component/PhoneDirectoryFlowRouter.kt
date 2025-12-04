package ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.component.di.PhoneDirectoryFlowDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.component.list.PhoneDirectoryListComponent
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.ui.api.IPhoneDirectoryFlowRouter

@OptIn(DelicateDecomposeApi::class)
class PhoneDirectoryFlowRouter(
    componentContext: ComponentContext,
    private val phoneDirectoryFlowDIComponent: PhoneDirectoryFlowDIComponent
) : IPhoneDirectoryFlowRouter {

    private val stackNavigation = StackNavigation<Configuration>()

    internal val childStack: Value<ChildStack<*, Child>> = componentContext.childStack(
        source = stackNavigation,
        serializer = Configuration.serializer(),
        handleBackButton = true,
        childFactory = ::childFactory,
        initialStack = { listOf(Configuration.PhoneDirectoryList) }
    )

    private fun childFactory(config: Configuration, componentContext: ComponentContext): Child {
        return when (config) {
            is Configuration.PhoneDirectoryList -> Child.PhoneDirectoryList(
                component = PhoneDirectoryListComponent(
                    componentContext = componentContext,
                    phoneDirectoryFlowDIComponent = phoneDirectoryFlowDIComponent,
                )
            )
        }
    }

    internal sealed interface Child {
        class PhoneDirectoryList(val component: PhoneDirectoryListComponent) : Child
    }

    @Serializable
    internal sealed class Configuration {
        @Serializable
        data object PhoneDirectoryList : Configuration()
    }

    override fun back() {
        stackNavigation.pop()
        (childStack.active.instance as Child.PhoneDirectoryList).component.viewModel.getData()
    }
}