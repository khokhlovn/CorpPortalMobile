package ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list

import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.ui.base.BaseStateViewModel
import ru.kama_diesel.corp_portal_mobile.common.ui.navigation.RouterHolder
import ru.kama_diesel.corp_portal_mobile.feature.shop.domain.di.ShopListScope
import ru.kama_diesel.corp_portal_mobile.feature.shop.domain.usecase.AddToCartUseCase
import ru.kama_diesel.corp_portal_mobile.feature.shop.domain.usecase.GetCartDataUseCase
import ru.kama_diesel.corp_portal_mobile.feature.shop.domain.usecase.GetShopListUseCase
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.api.IShopFlowRouter
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.model.CartAddingState
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.model.ShopListDialog
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.model.ShopListViewState

@ShopListScope
@Inject
class ShopListViewModel(
    private val getShopListUseCase: GetShopListUseCase,
    private val getCartDataUseCase: GetCartDataUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    routerHolder: RouterHolder<IShopFlowRouter>,
    private val initialState: ShopListViewState,
) : BaseStateViewModel<ShopListViewState>() {

    private val router by routerHolder

    init {
        getData()
    }

    fun getData() {
        getShopList()
        getCartData()
    }

    fun onShopItemClick(shopItemIndex: Int) {
        setState {
            copy(
                dialog = ShopListDialog.Details(shopItem = sortedShopItems[shopItemIndex]),
            )
        }
    }

    fun onCloseDialogClick() {
        setState {
            copy(
                dialog = ShopListDialog.No,
            )
        }
    }

    fun onSorterChange(sorter: Sorter) {
        setState {
            copy(
                selectedSorter = sorter,
            )
        }
        sortAndFilterItems()
    }

    fun onFilterChange(filter: Filter) {
        setState {
            copy(
                selectedFilter = filter,
            )
        }
        sortAndFilterItems()
    }

    fun onResetFilters() {
        setState {
            copy(
                selectedSorter = Sorter.PriceIncreasing,
                selectedFilter = Filter.All,
                sortedShopItems = shopItems,
            )
        }
    }

    fun addToCart(itemId: Int) {
        setState {
            copy(
                cartAddingState = CartAddingState.Adding,
            )
        }
        coroutineScope.launch {
            addToCartUseCase(itemId = itemId, quantity = 1)
            setState {
                copy(
                    cartAddingState = CartAddingState.No,
                )
            }
            getCartData()
        }
    }

    fun toCart() {
        router.toCart(
            shopItems = currentState.shopItems.filter {
                it.id in currentState.cartItems.map { cartItem -> cartItem.itemId }
            }
        )
    }

    private fun sortAndFilterItems() {
        coroutineScope.launch {
            setState {
                copy(
                    sortedShopItems = shopItems.filter {
                        when (selectedFilter) {
                            Filter.All -> true
                            Filter.Available -> it.isAvailable
                            Filter.NotAvailable -> !it.isAvailable
                        }
                    }.sortedWith(
                        when (selectedSorter) {
                            Sorter.PriceIncreasing -> compareBy { it.price }
                            Sorter.PriceDecreasing -> compareByDescending { it.price }
                            Sorter.Name -> compareBy { it.name }
                        }
                    ),
                    isLoading = false,
                    dialog = ShopListDialog.No,
                )
            }
        }
    }

    private fun getShopList() {
        coroutineScope.launch {
            val shopItems = getShopListUseCase()
            setState {
                copy(
                    shopItems = shopItems,
                )
            }
            sortAndFilterItems()
        }
    }

    private fun getCartData() {
        coroutineScope.launch {
            val cartItems = getCartDataUseCase()
            setState {
                copy(
                    cartItems = cartItems,
                )
            }
        }
    }

    override fun createInitialState() = initialState
}
