package ru.kama_diesel.corp_portal_mobile.feature.articles.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.ArticlesListScreenContainer

@Composable
fun ArticlesFlowScreenComponent(articlesFlowComponent: ArticlesFlowComponent) {
    val childStack by articlesFlowComponent.router.childStack.subscribeAsState()

    Children(
        stack = childStack,
        animation = stackAnimation(slide())
    ) {
        when (val child = it.instance) {
            is ArticlesFlowRouter.Child.ArticlesList -> {
                ArticlesListScreenContainer(child.component.viewModel)
            }
        }
    }
}