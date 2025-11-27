package ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.orders

import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.ui.base.BaseStateViewModel
import ru.kama_diesel.corp_portal_mobile.common.ui.navigation.RouterHolder
import ru.kama_diesel.corp_portal_mobile.feature.shop.domain.di.OrdersScope
import ru.kama_diesel.corp_portal_mobile.feature.shop.domain.usecase.GetOrdersUseCase
import ru.kama_diesel.corp_portal_mobile.feature.shop.domain.usecase.GetShopListUseCase
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.api.IShopFlowRouter
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.orders.model.OrdersViewState

@OrdersScope
@Inject
class OrdersViewModel(
    private val getOrdersUseCase: GetOrdersUseCase,
    private val getShopListUseCase: GetShopListUseCase,
    routerHolder: RouterHolder<IShopFlowRouter>,
    private val initialState: OrdersViewState,
) : BaseStateViewModel<OrdersViewState>() {

    private val router by routerHolder

    init {
        getData()
    }

    fun back() {
        router.back()
    }

    fun getData() {
        getOrdersData()
    }

    private fun getOrdersData() {
        coroutineScope.launch {
            val orderItems = getOrdersUseCase()
            val shopItems = getShopListUseCase()
            if (orderItems.isEmpty()) {
                back()
            } else {
                setState {
                    copy(
                        orderItems = orderItems,
                        shopItems = shopItems,
                        isLoading = false,
                    )
                }
            }
        }
    }

    override fun createInitialState() = initialState
}
