package ru.kama_diesel.corp_portal_mobile.feature.profile.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import ru.kama_diesel.corp_portal_mobile.feature.profile.component.di.ProfileFlowDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.profile.component.list.ProfileComponent
import ru.kama_diesel.corp_portal_mobile.feature.profile.ui.api.IProfileFlowRouter

@OptIn(DelicateDecomposeApi::class)
class ProfileFlowRouter(
    componentContext: ComponentContext,
    private val profileFlowDIComponent: ProfileFlowDIComponent
) : IProfileFlowRouter {

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
            is Configuration.PhoneDirectoryList -> Child.Profile(
                component = ProfileComponent(
                    componentContext = componentContext,
                    profileFlowDIComponent = profileFlowDIComponent,
                )
            )
        }
    }

    internal sealed interface Child {
        class Profile(val component: ProfileComponent) : Child
    }

    @Serializable
    internal sealed class Configuration {
        @Serializable
        data object PhoneDirectoryList : Configuration()
    }

    override fun back() {
        stackNavigation.pop()
        (childStack.active.instance as Child.Profile).component.viewModel.getData()
    }
}