package ru.kama_diesel.corp_portal_mobile.feature.articles.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import ru.kama_diesel.corp_portal_mobile.feature.articles.component.api.IArticlesComponentDependencies
import ru.kama_diesel.corp_portal_mobile.feature.articles.component.di.ArticlesFlowDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.articles.component.di.create

class ArticlesFlowComponent(
    componentContext: ComponentContext,
    componentDependencies: IArticlesComponentDependencies
) : ComponentContext by componentContext {

    private val diComponent = instanceKeeper.getOrCreate {
        ArticlesFlowDIComponent::class.create(componentDependencies)
    }

    val router = ArticlesFlowRouter(componentContext, diComponent)

    init {
        diComponent.getRouterHolder().updateInstance(router)
    }
}