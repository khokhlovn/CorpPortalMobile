package ru.kama_diesel.corp_portal_mobile.feature.articles.component.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import ru.kama_diesel.corp_portal_mobile.common.component.registerAndGetSavedState
import ru.kama_diesel.corp_portal_mobile.feature.articles.component.di.ArticlesFlowDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.articles.component.list.di.ArticlesListDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.articles.component.list.di.create
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.model.ArticlesListViewState

class ArticlesListComponent(
    componentContext: ComponentContext,
    articlesFlowDIComponent: ArticlesFlowDIComponent,
) : ComponentContext by componentContext {

    private val savedState: ArticlesListViewState = registerAndGetSavedState(
        key = ARTICLES_LIST_SAVED_STATE,
        initialValue = ArticlesListViewState(
            articleItems = listOf(),
        ),
        deserialization = ArticlesListViewState.serializer(),
        serialization = ArticlesListViewState.serializer()
    ) {
        viewModel.currentState
    }

    private val diComponent = instanceKeeper.getOrCreate {
        ArticlesListDIComponent::class.create(articlesFlowDIComponent, savedState)
    }

    val viewModel = diComponent.viewModel

    companion object Companion {
        private const val ARTICLES_LIST_SAVED_STATE = "ARTICLES_LIST_SAVED_STATE"
    }
}
