package ru.kama_diesel.corp_portal_mobile.feature.articles.component.list.di

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import ru.kama_diesel.corp_portal_mobile.feature.articles.component.di.ArticlesFlowDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.articles.domain.di.ArticlesListScope
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.ArticlesListViewModel
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.model.ArticlesListViewState

@ArticlesListScope
@Component
abstract class ArticlesListDIComponent(
    @Component val parent: ArticlesFlowDIComponent,
    private val initialState: ArticlesListViewState,
) : InstanceKeeper.Instance {

    abstract val viewModel: ArticlesListViewModel

    @Provides
    protected fun getInitialState(): ArticlesListViewState = initialState

    override fun onDestroy() {
        viewModel.onDestroy()
    }
}
