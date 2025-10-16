package ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list

import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.ui.base.BaseStateViewModel
import ru.kama_diesel.corp_portal_mobile.common.ui.navigation.RouterHolder
import ru.kama_diesel.corp_portal_mobile.feature.articles.domain.api.ILogoutUseCase
import ru.kama_diesel.corp_portal_mobile.feature.articles.domain.di.ArticlesListScope
import ru.kama_diesel.corp_portal_mobile.feature.articles.domain.usecase.ObserveArticlesListUseCase
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.api.IArticlesFlowRouter
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.model.ArticlesListViewState

@ArticlesListScope
@Inject
class ArticlesListViewModel(
    private val observeArticlesList: ObserveArticlesListUseCase,
    routerHolder: RouterHolder<IArticlesFlowRouter>,
    private val logout: ILogoutUseCase,
    private val initialState: ArticlesListViewState,
) : BaseStateViewModel<ArticlesListViewState>() {

    private val router by routerHolder

    init {
        initObservers()
    }

    fun onLogoutClick() {
        logout()
    }

    private fun initObservers() {
        coroutineScope.launch {
            val articleItems = observeArticlesList()
            setState {
                copy(articleItems = articleItems)
            }
        }
    }

    override fun createInitialState() = initialState
}
