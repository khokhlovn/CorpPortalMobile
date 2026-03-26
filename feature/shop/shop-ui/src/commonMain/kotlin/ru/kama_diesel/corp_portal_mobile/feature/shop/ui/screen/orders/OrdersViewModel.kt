package ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.orders

import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.ui.base.BaseStateViewModel
import ru.kama_diesel.corp_portal_mobile.common.ui.navigation.RouterHolder
import ru.kama_diesel.corp_portal_mobile.feature.shop.domain.di.OrdersScope
import ru.kama_diesel.corp_portal_mobile.feature.shop.domain.usecase.CancelOrderUseCase
import ru.kama_diesel.corp_portal_mobile.feature.shop.domain.usecase.GetAllShopListUseCase
import ru.kama_diesel.corp_portal_mobile.feature.shop.domain.usecase.GetOrdersUseCase
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.api.IShopFlowRouter
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.orders.model.OrderItemUIModel
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.orders.model.OrdersViewState

@OrdersScope
@Inject
class OrdersViewModel(
    private val getOrdersUseCase: GetOrdersUseCase,
    private val getAllShopListUseCase: GetAllShopListUseCase,
    private val cancelOrderUseCase: CancelOrderUseCase,
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

    fun onCancelOrderClick(cartId: Int) {
        coroutineScope.launch {
            setState {
                copy(
                    isLoading = true,
                )
            }
            cancelOrderUseCase(cartId = cartId)
            getOrdersData()
        }
    }

    private fun getOrdersData() {
        coroutineScope.launch {
            val orderItems = getOrdersUseCase()
            val shopItems = getAllShopListUseCase()
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
                        Sorter.SumIncreasing -> compareBy<OrderItemUIModel> { it.totalSum }.thenBy { it.id }
                        Sorter.SumDecreasing -> compareByDescending<OrderItemUIModel> { it.totalSum }.thenByDescending { it.id }
                        Sorter.DateIncreasing -> compareBy<OrderItemUIModel> {
                            val dateTimeFormatter = LocalDate.Format {
                                @OptIn(FormatStringsInDatetimeFormats::class)
                                byUnicodePattern("dd.MM.yyyy")
                            }
                            LocalDate.parse(it.date, dateTimeFormatter)
                        }.thenBy {
                            it.id
                        }

                        Sorter.DateDecreasing -> compareByDescending<OrderItemUIModel> {
                            val dateTimeFormatter = LocalDate.Format {
                                @OptIn(FormatStringsInDatetimeFormats::class)
                                byUnicodePattern("dd.MM.yyyy")
                            }
                            LocalDate.parse(it.date, dateTimeFormatter)
                        }.thenByDescending {
                            it.id
                        }
                    }
                )
            )
        }
    }

    override fun createInitialState() = initialState
}
