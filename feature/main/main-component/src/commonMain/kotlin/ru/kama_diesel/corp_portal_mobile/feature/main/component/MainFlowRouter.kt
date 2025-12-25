package ru.kama_diesel.corp_portal_mobile.feature.main.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.pages.*
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import ru.kama_diesel.corp_portal_mobile.feature.articles.component.ArticlesFlowComponent
import ru.kama_diesel.corp_portal_mobile.feature.main.component.di.MainFlowDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.main.ui.api.IMainFlowRouter
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.component.PhoneDirectoryFlowComponent
import ru.kama_diesel.corp_portal_mobile.feature.profile.component.ProfileFlowComponent
import ru.kama_diesel.corp_portal_mobile.feature.shop.component.ShopFlowComponent
import ru.kama_diesel.corp_portal_mobile.feature.shop.component.ShopFlowRouter
import ru.kama_diesel.corp_portal_mobile.feature.top.component.TopFlowComponent

@OptIn(ExperimentalDecomposeApi::class)
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

            is Configuration.Shop -> PagesChild.ShopFlow(
                component = ShopFlowComponent(componentContext, config.initialConfiguration, mainFlowDIComponent.shopComponentDependencies)
            )

            Configuration.PhoneDirectory -> PagesChild.PhoneDirectoryFlow(
                component = PhoneDirectoryFlowComponent(
                    componentContext,
                    mainFlowDIComponent.phoneDirectoryComponentDependencies
                )
            )

            Configuration.Top -> PagesChild.TopFlow(
                component = TopFlowComponent(
                    componentContext,
                    mainFlowDIComponent.topComponentDependencies
                )
            )

            Configuration.Profile -> PagesChild.ProfileFlow(
                component = ProfileFlowComponent(
                    componentContext,
                    mainFlowDIComponent.profileComponentDependencies
                )
            )
        }
    }

    internal sealed interface PagesChild {
        data class ArticlesFlow(val component: ArticlesFlowComponent) : PagesChild
        data class ShopFlow(val component: ShopFlowComponent) : PagesChild
        data class PhoneDirectoryFlow(val component: PhoneDirectoryFlowComponent) : PagesChild
        data class TopFlow(val component: TopFlowComponent) : PagesChild
        data class ProfileFlow(val component: ProfileFlowComponent) : PagesChild
    }

    @Serializable
    internal sealed class Configuration {

        @Serializable
        data object Articles : Configuration()

        @Serializable
        data class Shop(val initialConfiguration: ShopFlowRouter.Configuration) : Configuration()

        @Serializable
        data object PhoneDirectory : Configuration()

        @Serializable
        data object Top : Configuration()

        @Serializable
        data object Profile : Configuration()
    }

    override fun toArticles() {
        pagesNavigation.clear()
        pagesNavigation.setItems { items -> items.plus(Configuration.Articles) }
        pagesNavigation.selectFirst()
    }

    override fun toShop() {
        pagesNavigation.clear()
        pagesNavigation.setItems { items -> items.plus(Configuration.Shop(initialConfiguration = ShopFlowRouter.Configuration.ShopList)) }
        pagesNavigation.selectFirst()
    }

    override fun toCart() {
        pagesNavigation.clear()
        pagesNavigation.setItems { items -> items.plus(Configuration.Shop(initialConfiguration = ShopFlowRouter.Configuration.Cart)) }
        pagesNavigation.selectFirst()
    }

    override fun toOrdersHistory() {
        pagesNavigation.clear()
        pagesNavigation.setItems { items -> items.plus(Configuration.Shop(initialConfiguration = ShopFlowRouter.Configuration.Orders)) }
        pagesNavigation.selectFirst()
    }

    override fun toPhoneDirectory() {
        pagesNavigation.clear()
        pagesNavigation.setItems { items -> items.plus(Configuration.PhoneDirectory) }
        pagesNavigation.selectFirst()
    }

    override fun toTop() {
        pagesNavigation.clear()
        pagesNavigation.setItems { items -> items.plus(Configuration.Top) }
        pagesNavigation.selectFirst()
    }

    override fun toProfile() {
        pagesNavigation.clear()
        pagesNavigation.setItems { items -> items.plus(Configuration.Profile) }
        pagesNavigation.selectFirst()
    }
}
