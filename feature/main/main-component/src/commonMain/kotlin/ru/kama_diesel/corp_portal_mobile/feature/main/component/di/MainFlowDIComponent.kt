package ru.kama_diesel.corp_portal_mobile.feature.main.component.di

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import ru.kama_diesel.corp_portal_mobile.common.ui.navigation.RouterHolder
import ru.kama_diesel.corp_portal_mobile.feature.articles.component.api.IArticlesComponentDependencies
import ru.kama_diesel.corp_portal_mobile.feature.main.component.api.IMainComponentDependencies
import ru.kama_diesel.corp_portal_mobile.feature.main.component.di.dependencies.*
import ru.kama_diesel.corp_portal_mobile.feature.main.ui.api.IMainFlowRouter
import ru.kama_diesel.corp_portal_mobile.feature.main.ui.screen.MainViewModel
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.component.api.IPhoneDirectoryComponentDependencies
import ru.kama_diesel.corp_portal_mobile.feature.profile.component.api.IProfileComponentDependencies
import ru.kama_diesel.corp_portal_mobile.feature.root.domain.di.MainFlowScope
import ru.kama_diesel.corp_portal_mobile.feature.shop.component.api.IShopComponentDependencies
import ru.kama_diesel.corp_portal_mobile.feature.top.component.api.ITopComponentDependencies

@MainFlowScope
@Component
internal abstract class MainFlowDIComponent(
    @Component val dependencies: IMainComponentDependencies
) : InstanceKeeper.Instance {

    private val routerHolder = RouterHolder<IMainFlowRouter>()

    abstract val viewModel: MainViewModel

    abstract val articlesComponentDependencies: IArticlesComponentDependencies

    abstract val shopComponentDependencies: IShopComponentDependencies

    abstract val phoneDirectoryComponentDependencies: IPhoneDirectoryComponentDependencies

    abstract val profileComponentDependencies: IProfileComponentDependencies

    abstract val topComponentDependencies: ITopComponentDependencies

    @Provides
    fun getRouterHolder(): RouterHolder<IMainFlowRouter> = routerHolder

    @Provides
    protected fun bind(it: ArticlesComponentDependencies): IArticlesComponentDependencies = it

    @Provides
    protected fun bind(it: ShopComponentDependencies): IShopComponentDependencies = it

    @Provides
    protected fun bind(it: PhoneDirectoryComponentDependencies): IPhoneDirectoryComponentDependencies = it

    @Provides
    protected fun bind(it: ProfileComponentDependencies): IProfileComponentDependencies = it

    @Provides
    protected fun bind(it: TopComponentDependencies): ITopComponentDependencies = it

    override fun onDestroy() {
        viewModel.onDestroy()
    }
}
