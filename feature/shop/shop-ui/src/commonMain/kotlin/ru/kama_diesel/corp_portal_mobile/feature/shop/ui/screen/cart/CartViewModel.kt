package ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.cart

import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.ui.base.BaseStateViewModel
import ru.kama_diesel.corp_portal_mobile.common.ui.navigation.RouterHolder
import ru.kama_diesel.corp_portal_mobile.feature.shop.domain.di.CartScope
import ru.kama_diesel.corp_portal_mobile.feature.shop.domain.usecase.AddToCartUseCase
import ru.kama_diesel.corp_portal_mobile.feature.shop.domain.usecase.GetCartDataUseCase
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.api.IShopFlowRouter
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.cart.model.CartAddingState
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.cart.model.CartDialog
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.cart.model.CartViewState

@CartScope
@Inject
class CartViewModel(
    private val getCartDataUseCase: GetCartDataUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    routerHolder: RouterHolder<IShopFlowRouter>,
    private val initialState: CartViewState,
) : BaseStateViewModel<CartViewState>() {

    private val router by routerHolder

    init {
        getData()
    }

    fun back() {
        router.back()
    }

    fun getData() {
        getCartData()
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

    private fun getCartData() {
        coroutineScope.launch {
            val cartItems = getCartDataUseCase()
            setState {
                copy(
                    cartItems = cartItems,
                    isLoading = false,
                    dialog = CartDialog.No,
                )
            }
        }
    }

    override fun createInitialState() = initialState
}
