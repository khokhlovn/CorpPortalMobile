package ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.ui.base.BaseStateViewModel
import ru.kama_diesel.corp_portal_mobile.common.ui.navigation.RouterHolder
import ru.kama_diesel.corp_portal_mobile.feature.shop.domain.di.ShopListScope
import ru.kama_diesel.corp_portal_mobile.feature.shop.domain.usecase.AddToCartUseCase
import ru.kama_diesel.corp_portal_mobile.feature.shop.domain.usecase.DropCartItemUseCase
import ru.kama_diesel.corp_portal_mobile.feature.shop.domain.usecase.GetBalanceUseCase
import ru.kama_diesel.corp_portal_mobile.feature.shop.domain.usecase.GetCartDataUseCase
import ru.kama_diesel.corp_portal_mobile.feature.shop.domain.usecase.GetOrdersUseCase
import ru.kama_diesel.corp_portal_mobile.feature.shop.domain.usecase.GetShopListUseCase
import ru.kama_diesel.corp_portal_mobile.feature.shop.domain.usecase.UpdateCartItemUseCase
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.api.IShopFlowRouter
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.model.CartAddingState
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.model.ShopItemUIModel
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.model.ShopListDialog
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.model.ShopListViewState

@ShopListScope
@Inject
class ShopListViewModel(
    private val getShopListUseCase: GetShopListUseCase,
    private val getCartDataUseCase: GetCartDataUseCase,
    private val getOrdersUseCase: GetOrdersUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val updateCartItemUseCase: UpdateCartItemUseCase,
    private val dropCartItemUseCase: DropCartItemUseCase,
    private val getBalanceUseCase: GetBalanceUseCase,
    routerHolder: RouterHolder<IShopFlowRouter>,
    private val initialState: ShopListViewState,
) : BaseStateViewModel<ShopListViewState>() {

    private val router by routerHolder
    private var itemQuantityChangeDebounceJob: Job? = null

    init {
        getData()
    }

    fun getData() {
        setState {
            copy(
                isLoading = true,
            )
        }
        getBalance()
        getShopList()
        getCartAndOrdersData()
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
                sortedShopItems = shopItems.map {
                    ShopItemUIModel(
                        id = it.id,
                        name = it.name,
                        description = it.description,
                        characteristics = it.characteristics,
                        partNumber = it.partNumber,
                        price = it.price,
                        imagePaths = it.imagePaths,
                        isAvailable = it.isAvailable,
                        isActive = it.isActive,
                        quantity = it.quantity,
                        cartAddingState = CartAddingState.No,
                    )
                },
            )
        }
    }

    fun addToCart(itemId: Int) {
        setState {
            copy(
                sortedShopItems = sortedShopItems.map {
                    if (it.id != itemId) {
                        it
                    } else {
                        it.copy(
                            cartAddingState = CartAddingState.Adding,
                        )
                    }
                }
            )
        }
        coroutineScope.launch {
            addToCartUseCase(itemId = itemId, quantity = 1)
            getCartAndOrdersData()
        }
    }

    fun onUpdateCartItemQuantityClick(inCartItemId: Int, quantity: Int) {
        with(currentState.cartItems.first { cartItem -> cartItem.inCartItemId == inCartItemId }) {
            setState {
                copy(
                    cartItems = cartItems.map {
                        if (it.inCartItemId != inCartItemId) {
                            it
                        } else {
                            it.copy(
                                quantity = quantity,
                            )
                        }
                    }
                )
            }
            itemQuantityChangeDebounceJob?.cancel()
            itemQuantityChangeDebounceJob = coroutineScope.launch {
                if (quantity > 0) {
                    delay(500)
                }
                setState {
                    copy(
                        sortedShopItems = sortedShopItems.map {
                            if (it.id != itemId) {
                                it
                            } else {
                                it.copy(
                                    cartAddingState = CartAddingState.Adding,
                                )
                            }
                        }
                    )
                }
                updateCartItemUseCase(inCartItemId = inCartItemId, quantity = quantity)
                getCartAndOrdersData()
            }
        }
    }

    fun onDeleteCartItemClick(inCartItemId: Int) {
        with(currentState.cartItems.first { cartItem -> cartItem.inCartItemId == inCartItemId }) {
            setState {
                copy(
                    sortedShopItems = sortedShopItems.map {
                        if (it.id != itemId) {
                            it
                        } else {
                            it.copy(
                                cartAddingState = CartAddingState.Adding,
                            )
                        }
                    }
                )
            }
            coroutineScope.launch {
                dropCartItemUseCase(inCartItemId = inCartItemId)
                getCartAndOrdersData()
            }
        }
    }

    fun toCart() {
        router.toCart()
    }

    fun toOrders() {
        router.toOrders()
    }

    private fun sortAndFilterItems() {
        coroutineScope.launch {
            setState {
                copy(
                    sortedShopItems = shopItems.filter {
                        when (selectedFilter) {
                            Filter.All -> true
                            Filter.Available -> it.isActive
                            Filter.NotAvailable -> !it.isActive
                        }
                    }.sortedWith(
                        when (selectedSorter) {
                            Sorter.PriceIncreasing -> compareBy { it.price }
                            Sorter.PriceDecreasing -> compareByDescending { it.price }
                            Sorter.Name -> compareBy { it.name }
                        }
                    ).map {
                        ShopItemUIModel(
                            id = it.id,
                            name = it.name,
                            description = it.description,
                            characteristics = it.characteristics,
                            partNumber = it.partNumber,
                            price = it.price,
                            imagePaths = it.imagePaths,
                            isAvailable = it.isAvailable,
                            isActive = it.isActive,
                            quantity = it.quantity,
                            cartAddingState = CartAddingState.No,
                        )
                    },
                    isLoading = false,
                    dialog = ShopListDialog.No,
                )
            }
        }
    }

    private fun getBalance() {
        coroutineScope.launch {
            val balance = getBalanceUseCase()
            setState {
                copy(
                    balance = balance,
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

    private fun getCartAndOrdersData() {
        coroutineScope.launch {
            val cartItems = getCartDataUseCase()
            val orderItems = getOrdersUseCase()
            setState {
                copy(
                    sortedShopItems = sortedShopItems.map { it.copy(cartAddingState = CartAddingState.No) },
                    cartItems = cartItems,
                    orderItems = orderItems,
                )
            }
        }
    }

    override fun createInitialState() = initialState
}
