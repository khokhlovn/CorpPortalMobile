package ru.kama_diesel.corp_portal_mobile.feature.shop.component.list.di

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import ru.kama_diesel.corp_portal_mobile.feature.shop.component.di.ShopFlowDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.shop.domain.di.ShopListScope
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.ShopListViewModel
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.model.ShopListViewState

@ShopListScope
@Component
abstract class ShopListDIComponent(
    @Component val parent: ShopFlowDIComponent,
    private val initialState: ShopListViewState,
) : InstanceKeeper.Instance {

    abstract val viewModel: ShopListViewModel

    @Provides
    protected fun getInitialState(): ShopListViewState = initialState

    override fun onDestroy() {
        viewModel.onDestroy()
    }
}
