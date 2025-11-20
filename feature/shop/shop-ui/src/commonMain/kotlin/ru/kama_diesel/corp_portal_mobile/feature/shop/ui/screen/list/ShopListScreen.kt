package ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import ru.kama_diesel.corp_portal_mobile.common.ui.component.LoadingDialog
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.details.ShopItemDetailsDialog
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.model.ShopListDialog
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.model.ShopListViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopListScreen(
    viewState: ShopListViewState,
    onRefresh: () -> Unit,
    onSorterChange: (Sorter) -> Unit,
    onFilterChange: (Filter) -> Unit,
    onResetFilters: () -> Unit,
    onShopItemClick: (Int) -> Unit,
    onAddToCartClick: (Int) -> Unit,
    onToCartClick: () -> Unit,
    onCloseDialogClick: () -> Unit,
) {
    ShopListScreenContent(
        shopItems = viewState.sortedShopItems,
        cartItems = viewState.cartItems,
        selectedSorter = viewState.selectedSorter,
        selectedFilter = viewState.selectedFilter,
        isRefreshing = viewState.isLoading,
        cartAddingState = viewState.cartAddingState,
        onRefresh = onRefresh,
        onShopItemClick = onShopItemClick,
        onSorterChange = onSorterChange,
        onFilterChange = onFilterChange,
        onResetFilters = onResetFilters,
        onAddToCartClick = onAddToCartClick,
        onToCartClick = onToCartClick,
    )

    when (val dialog = viewState.dialog) {
        ShopListDialog.Loading -> LoadingDialog()
        is ShopListDialog.Details -> ShopItemDetailsDialog(
            shopItem = dialog.shopItem,
            onCloseClick = onCloseDialogClick,
            onAddToCartClick = { },
        )

        ShopListDialog.No -> Unit
    }
}
