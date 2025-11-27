package ru.kama_diesel.corp_portal_mobile.feature.shop.component.list.di

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import ru.kama_diesel.corp_portal_mobile.feature.shop.component.di.ShopFlowDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.shop.domain.di.OrdersScope
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.orders.OrdersViewModel
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.orders.model.OrdersViewState

@OrdersScope
@Component
abstract class OrdersDiComponent(
    @Component val parent: ShopFlowDIComponent,
    private val initialState: OrdersViewState,
) : InstanceKeeper.Instance {

    abstract val viewModel: OrdersViewModel

    @Provides
    protected fun getInitialState(): OrdersViewState = initialState

    override fun onDestroy() {
        viewModel.onDestroy()
    }
}
