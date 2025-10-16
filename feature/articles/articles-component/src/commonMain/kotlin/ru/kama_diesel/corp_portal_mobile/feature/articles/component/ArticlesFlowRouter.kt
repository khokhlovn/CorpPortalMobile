package ru.kama_diesel.corp_portal_mobile.feature.articles.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import ru.kama_diesel.corp_portal_mobile.feature.articles.component.di.ArticlesFlowDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.articles.component.list.ArticlesListComponent
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.api.IArticlesFlowRouter

class ArticlesFlowRouter(
    componentContext: ComponentContext,
    private val articlesFlowDIComponent: ArticlesFlowDIComponent
) : IArticlesFlowRouter {

    private val stackNavigation = StackNavigation<Configuration>()

    internal val childStack: Value<ChildStack<*, Child>> = componentContext.childStack(
        source = stackNavigation,
        serializer = Configuration.serializer(),
        handleBackButton = true,
        childFactory = ::childFactory,
        initialStack = { listOf(Configuration.ArticlesList) }
    )

    private fun childFactory(config: Configuration, componentContext: ComponentContext): Child {
        return when (config) {
            is Configuration.ArticlesList -> Child.ArticlesList(
                component = ArticlesListComponent(
                    componentContext = componentContext,
                    articlesFlowDIComponent = articlesFlowDIComponent
                )
            )
        }
    }

    internal sealed interface Child {
        class ArticlesList(val component: ArticlesListComponent) : Child
    }

    @Serializable
    internal sealed class Configuration {
        @Serializable
        data object ArticlesList : Configuration()
    }

    override fun back() {
        stackNavigation.pop()
    }
}