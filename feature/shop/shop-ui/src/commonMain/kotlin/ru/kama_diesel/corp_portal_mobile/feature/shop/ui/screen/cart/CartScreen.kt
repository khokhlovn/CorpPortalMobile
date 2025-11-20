package ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.cart

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import ru.kama_diesel.corp_portal_mobile.common.ui.component.LoadingDialog
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.cart.model.CartDialog
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.cart.model.CartViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    viewState: CartViewState,
    onRefresh: () -> Unit,
    onBackClick: () -> Unit,
    onAddToCartClick: (Int) -> Unit,
) {
    CartScreenContent(
        cartItems = viewState.cartItems,
        shopItems = viewState.shopItems,
        isRefreshing = viewState.isLoading,
        cartAddingState = viewState.cartAddingState,
        onRefresh = onRefresh,
        onBackClick = onBackClick,
        onAddToCartClick = onAddToCartClick,
    )

    when (viewState.dialog) {
        CartDialog.Loading -> LoadingDialog()
        CartDialog.No -> Unit
    }
}
