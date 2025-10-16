package ru.kama_diesel.corp_portal_mobile.feature.articles.component.di

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IArticlesRepository
import ru.kama_diesel.corp_portal_mobile.common.ui.navigation.RouterHolder
import ru.kama_diesel.corp_portal_mobile.feature.articles.component.api.IArticlesComponentDependencies
import ru.kama_diesel.corp_portal_mobile.feature.articles.data.repository.ArticlesRepository
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.api.IArticlesFlowRouter

@Component
abstract class ArticlesFlowDIComponent(
    @Component val dependencies: IArticlesComponentDependencies
) : InstanceKeeper.Instance {

    private val routerHolder = RouterHolder<IArticlesFlowRouter>()

    @Provides
    fun getRouterHolder(): RouterHolder<IArticlesFlowRouter> = routerHolder

    val ArticlesRepository.bind: IArticlesRepository
        @Provides get() = this
}
