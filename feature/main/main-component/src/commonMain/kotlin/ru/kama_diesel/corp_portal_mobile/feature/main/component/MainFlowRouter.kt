package ru.kama_diesel.corp_portal_mobile.feature.main.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.pages.*
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import ru.kama_diesel.corp_portal_mobile.feature.articles.component.ArticlesFlowComponent
import ru.kama_diesel.corp_portal_mobile.feature.main.component.di.MainFlowDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.main.ui.api.IMainFlowRouter
import ru.kama_diesel.corp_portal_mobile.feature.shop.component.ShopFlowComponent

internal class MainFlowRouter(
    componentContext: ComponentContext,
    private val mainFlowDIComponent: MainFlowDIComponent
) : IMainFlowRouter {

    private val pagesNavigation = PagesNavigation<Configuration>()

    internal val childPages: Value<ChildPages<*, PagesChild>> = componentContext.childPages(
        source = pagesNavigation,
        initialPages = {
            Pages(
                items = listOf(
                    Configuration.Articles,
                    Configuration.Shop
                ),
                selectedIndex = 0,
            )
        },
        serializer = Configuration.serializer(),
        handleBackButton = false,
        childFactory = ::pagesChild
    )

    private fun pagesChild(config: Configuration, componentContext: ComponentContext): PagesChild {
        return when (config) {
            Configuration.Articles -> PagesChild.ArticlesFlow(
                component = ArticlesFlowComponent(componentContext, mainFlowDIComponent.articlesComponentDependencies)
            )

            Configuration.Shop -> PagesChild.ShopFlow(
                component = ShopFlowComponent(componentContext, mainFlowDIComponent.shopComponentDependencies)
            )
        }
    }

    internal sealed interface PagesChild {
        data class ArticlesFlow(val component: ArticlesFlowComponent) : PagesChild
        data class ShopFlow(val component: ShopFlowComponent) : PagesChild
    }

    @Serializable
    internal sealed class Configuration {

        @Serializable
        data object Articles : Configuration()

        @Serializable
        data object Shop : Configuration()
    }

    override fun toArticles() {
        pagesNavigation.select(0)
    }

    override fun toShop() {
        pagesNavigation.select(1)
    }
}
