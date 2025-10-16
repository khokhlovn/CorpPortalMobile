package ru.kama_diesel.corp_portal_mobile.feature.root.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import ru.kama_diesel.corp_portal_mobile.feature.articles.component.ArticlesFlowComponent
import ru.kama_diesel.corp_portal_mobile.feature.auth.component.AuthFlowComponent
import ru.kama_diesel.corp_portal_mobile.feature.root.component.di.RootFlowDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.root.ui.api.IRootFlowRouter

internal class RootFlowRouter(
    componentContext: ComponentContext,
    private val rootFlowDIComponent: RootFlowDIComponent
) : IRootFlowRouter {

    private val slotNavigation = SlotNavigation<Configuration>()

    internal val childSlot: Value<ChildSlot<*, SlotChild>> = componentContext.childSlot(
        source = slotNavigation,
        serializer = Configuration.serializer(),
        handleBackButton = false,
        childFactory = ::slotChild
    )

    private fun slotChild(config: Configuration, componentContext: ComponentContext): SlotChild {
        return when (config) {
            Configuration.Auth -> SlotChild.AuthFlow(
                component = AuthFlowComponent(componentContext, rootFlowDIComponent.authComponentDependencies)
            )

            Configuration.Articles -> SlotChild.ArticlesFlow(
                component = ArticlesFlowComponent(componentContext, rootFlowDIComponent.articlesComponentDependencies)
            )
        }
    }

    internal sealed interface SlotChild {
        data class AuthFlow(val component: AuthFlowComponent) : SlotChild
        data class ArticlesFlow(val component: ArticlesFlowComponent) : SlotChild
    }

    @Serializable
    internal sealed class Configuration {
        @Serializable
        data object Auth : Configuration()

        @Serializable
        data object Articles : Configuration()
    }

    override fun toAuth() {
        slotNavigation.activate(Configuration.Auth)
    }

    override fun toArticles() {
        slotNavigation.activate(Configuration.Articles)
    }
}