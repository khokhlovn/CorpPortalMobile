package ru.kama_diesel.corp_portal_mobile.feature.main.ui.screen

import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.ILogoutUseCase
import ru.kama_diesel.corp_portal_mobile.common.ui.base.BaseViewModel
import ru.kama_diesel.corp_portal_mobile.common.ui.navigation.RouterHolder
import ru.kama_diesel.corp_portal_mobile.feature.main.ui.api.IMainFlowRouter
import ru.kama_diesel.corp_portal_mobile.feature.root.domain.di.MainFlowScope

@MainFlowScope
@Inject
class MainViewModel(
    val logoutUseCase: ILogoutUseCase,
    routerHolder: RouterHolder<IMainFlowRouter>,
) : BaseViewModel() {

    private val router by routerHolder

    fun onArticlesClick() {
        router.toArticles()
    }

    fun onShopClick() {
        router.toShop()
    }

    fun onLogoutClick() {
        logoutUseCase()
    }
}