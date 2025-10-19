package ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list

import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.ui.base.BaseStateViewModel
import ru.kama_diesel.corp_portal_mobile.common.ui.navigation.RouterHolder
import ru.kama_diesel.corp_portal_mobile.feature.articles.domain.api.ILogoutUseCase
import ru.kama_diesel.corp_portal_mobile.feature.articles.domain.di.ArticlesListScope
import ru.kama_diesel.corp_portal_mobile.feature.articles.domain.usecase.GetArticlesListUseCase
import ru.kama_diesel.corp_portal_mobile.feature.articles.domain.usecase.GetTagsUseCase
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.api.IArticlesFlowRouter
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.model.ArticlesListViewState
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.model.TagItemUIModel

@ArticlesListScope
@Inject
class ArticlesListViewModel(
    private val getArticlesListUseCase: GetArticlesListUseCase,
    private val getTagsUseCase: GetTagsUseCase,
    routerHolder: RouterHolder<IArticlesFlowRouter>,
    private val logout: ILogoutUseCase,
    private val initialState: ArticlesListViewState,
) : BaseStateViewModel<ArticlesListViewState>() {

    private val router by routerHolder

    init {
        getData()
    }

    fun onLogoutClick() {
        logout()
    }

    fun getData() {
        coroutineScope.launch {
            val articleItems = getArticlesListUseCase()
            val tagItems = getTagsUseCase()
            setState {
                copy(
                    articleItems = articleItems,
                    tagItems = tagItems.map { tagItem -> TagItemUIModel(tagItem = tagItem, isChecked = false) },
                    isLoading = false,
                )
            }
        }
    }

    fun checkTag(id: String, isChecked: Boolean) {
        setState {
            copy(
                tagItems = tagItems.map { tagItemUIModel ->
                    tagItemUIModel.copy(
                        isChecked = if (tagItemUIModel.tagItem.id == id) isChecked else tagItemUIModel.isChecked
                    )
                }
            )
        }
    }

    override fun createInitialState() = initialState
}
