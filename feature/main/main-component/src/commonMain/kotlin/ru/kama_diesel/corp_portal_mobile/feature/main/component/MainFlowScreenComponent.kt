package ru.kama_diesel.corp_portal_mobile.feature.main.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import ru.kama_diesel.corp_portal_mobile.feature.articles.component.ArticlesFlowScreenComponent
import ru.kama_diesel.corp_portal_mobile.feature.main.ui.screen.MainScreenContainer
import ru.kama_diesel.corp_portal_mobile.feature.root.component.MainFlowComponent
import ru.kama_diesel.corp_portal_mobile.feature.shop.component.ShopFlowScreenComponent

@Composable
fun MainFlowScreenComponent(mainFlowComponent: MainFlowComponent) {
    val childPages by mainFlowComponent.router.childPages.subscribeAsState()

    MainScreenContainer(
        selectedIndex = when (childPages.items.first().instance) {
            is MainFlowRouter.PagesChild.ArticlesFlow -> 0
            is MainFlowRouter.PagesChild.ShopFlow -> 1
            null -> 0
        },
        tab = {
            when (val page = childPages.items[childPages.selectedIndex].instance) {
                is MainFlowRouter.PagesChild.ArticlesFlow -> ArticlesFlowScreenComponent(articlesFlowComponent = page.component)
                is MainFlowRouter.PagesChild.ShopFlow -> ShopFlowScreenComponent(shopFlowComponent = page.component)
                null -> Unit
            }
        },
        viewModel = mainFlowComponent.viewModel,
    )
}
