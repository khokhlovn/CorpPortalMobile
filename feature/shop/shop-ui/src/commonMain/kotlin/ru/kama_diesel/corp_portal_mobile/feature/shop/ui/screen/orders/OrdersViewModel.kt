package ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.orders

import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.ui.base.BaseStateViewModel
import ru.kama_diesel.corp_portal_mobile.common.ui.navigation.RouterHolder
import ru.kama_diesel.corp_portal_mobile.feature.shop.domain.di.OrdersScope
import ru.kama_diesel.corp_portal_mobile.feature.shop.domain.usecase.GetOrdersUseCase
import ru.kama_diesel.corp_portal_mobile.feature.shop.domain.usecase.GetShopListUseCase
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.api.IShopFlowRouter
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.orders.model.OrderItemUIModel
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

    fun onSorterChange(sorter: Sorter) {
        setState {
            copy(
                selectedSorter = sorter,
            )
        }
        resortItems()
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
                        orderItems = orderItems.map {
                            OrderItemUIModel(
                                id = it.id,
                                date = it.date,
                                status = it.status,
                                items = it.items,
                                totalSum = it.items.sumOf { positionItem ->
                                    shopItems.find { shopItem -> shopItem.id == positionItem.id }?.price?.times(
                                        positionItem.quantity
                                    )
                                        ?: 0
                                },
                            )
                        },
                        shopItems = shopItems,
                        isLoading = false,
                    )
                }
                resortItems()
            }
        }
    }

    private fun resortItems() {
        setState {
            copy(
                sortedOrderItems = orderItems.sortedWith(
                    when (selectedSorter) {
                        Sorter.SumIncreasing -> compareBy { it.totalSum }
                        Sorter.SumDecreasing -> compareByDescending { it.totalSum }
                        Sorter.DateIncreasing -> compareBy { it.date }
                        Sorter.DateDecreasing -> compareByDescending { it.date }
                    }
                )
            )
        }
    }

    override fun createInitialState() = initialState
}
