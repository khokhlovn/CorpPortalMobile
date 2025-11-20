package ru.kama_diesel.corp_portal_mobile.feature.shop.component.list.di

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import ru.kama_diesel.corp_portal_mobile.feature.shop.component.di.ShopFlowDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.shop.domain.di.CartScope
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.cart.CartViewModel
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.cart.model.CartViewState

@CartScope
@Component
abstract class CartDIComponent(
    @Component val parent: ShopFlowDIComponent,
    private val initialState: CartViewState,
) : InstanceKeeper.Instance {

    abstract val viewModel: CartViewModel

    @Provides
    protected fun getInitialState(): CartViewState = initialState

    override fun onDestroy() {
        viewModel.onDestroy()
    }
}
