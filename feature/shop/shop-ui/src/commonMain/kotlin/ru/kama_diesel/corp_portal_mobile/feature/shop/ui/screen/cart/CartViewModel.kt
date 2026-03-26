package ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.cart

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.ui.base.BaseStateViewModel
import ru.kama_diesel.corp_portal_mobile.common.ui.navigation.RouterHolder
import ru.kama_diesel.corp_portal_mobile.feature.shop.domain.di.CartScope
import ru.kama_diesel.corp_portal_mobile.feature.shop.domain.usecase.DropCartItemUseCase
import ru.kama_diesel.corp_portal_mobile.feature.shop.domain.usecase.GetAllShopListUseCase
import ru.kama_diesel.corp_portal_mobile.feature.shop.domain.usecase.GetBalanceUseCase
import ru.kama_diesel.corp_portal_mobile.feature.shop.domain.usecase.GetCartDataUseCase
import ru.kama_diesel.corp_portal_mobile.feature.shop.domain.usecase.MakeOrderUseCase
import ru.kama_diesel.corp_portal_mobile.feature.shop.domain.usecase.UpdateCartItemUseCase
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.api.IShopFlowRouter
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.cart.model.CartAddingState
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.cart.model.CartItemUIModel
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.cart.model.CartViewState
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.cart.model.MakingOrderState

@CartScope
@Inject
class CartViewModel(
    private val getCartDataUseCase: GetCartDataUseCase,
    private val getAllShopListUseCase: GetAllShopListUseCase,
    private val updateCartItemUseCase: UpdateCartItemUseCase,
    private val dropCartItemUseCase: DropCartItemUseCase,
    private val getBalanceUseCase: GetBalanceUseCase,
    private val makeOrderUseCase: MakeOrderUseCase,
    routerHolder: RouterHolder<IShopFlowRouter>,
    private val initialState: CartViewState,
) : BaseStateViewModel<CartViewState>() {

    private val router by routerHolder
    private var itemQuantityChangeDebounceJob: Job? = null

    init {
        getData()
    }

    fun back() {
        router.back()
    }

    fun getData() {
        getBalance()
        getCartData()
    }

    fun onUpdateCartItemQuantityClick(inCartItemId: Int, quantity: Int) {
        val newCartItems = currentState.cartItems.map {
            if (it.inCartItemId != inCartItemId) {
                it
            } else {
                it.copy(
                    quantity = quantity,
                )
            }
        }
        setState {
            copy(
                cartItems = newCartItems,
                totalSum = newCartItems.sumOf { cartItem ->
                    shopItems.find { shopItem -> shopItem.id == cartItem.itemId }?.price?.times(cartItem.quantity)
                        ?: 0
                },
            )
        }
        itemQuantityChangeDebounceJob?.cancel()
        itemQuantityChangeDebounceJob = coroutineScope.launch {
            if (quantity > 0) {
                delay(500)
            }
            setState {
                copy(
                    cartItems = cartItems.map {
                        if (it.inCartItemId != inCartItemId) {
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
            getCartData()
        }
    }

    fun onDeleteCartItemClick(inCartItemId: Int) {
        setState {
            copy(
                cartItems = cartItems.map {
                    if (it.inCartItemId != inCartItemId) {
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
            getCartData()
        }
    }

    fun onMakeOrderClick() {
        setState {
            copy(
                makingOrderState = MakingOrderState.Process,
            )
        }
        coroutineScope.launch {
            makeOrderUseCase()
            back()
        }
    }

    fun onCartItemCheckedChange(inCartItemId: Int, isChecked: Boolean) {
        setState {
            copy(
                cartItems = cartItems.map {
                    if (it.inCartItemId != inCartItemId) {
                        it
                    } else {
                        it.copy(
                            isChecked = isChecked,
                        )
                    }
                }
            )
        }
    }

    fun onSelectAllClick(isChecked: Boolean) {
        setState {
            copy(
                cartItems = cartItems.map {
                    it.copy(
                        isChecked = isChecked,
                    )
                }
            )
        }
    }

    fun onDropSelectedItems() {
        setState {
            copy(
                isLoading = true,
            )
        }
        coroutineScope.launch {
            currentState.cartItems.filter { it.isChecked }.map { it.inCartItemId }.forEach {
                dropCartItemUseCase(inCartItemId = it)
            }
            back()
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

    private fun getCartData() {
        coroutineScope.launch {
            val cartItems = getCartDataUseCase()
            val shopItems = getAllShopListUseCase()
            if (cartItems.isEmpty()) {
                back()
            } else {
                setState {
                    copy(
                        cartItems = cartItems.map {
                            CartItemUIModel(
                                inCartItemId = it.inCartItemId,
                                itemId = it.itemId,
                                quantity = it.quantity,
                                isChecked = false,
                                cartAddingState = CartAddingState.No,
                            )
                        },
                        shopItems = shopItems,
                        totalSum = cartItems.sumOf { cartItem ->
                            shopItems.find { shopItem -> shopItem.id == cartItem.itemId }?.price?.times(cartItem.quantity)
                                ?: 0
                        },
                        isLoading = false,
                    )
                }
            }
        }
    }

    override fun createInitialState() = initialState
}
