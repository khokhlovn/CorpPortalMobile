package ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun ShopListScreenContainer(
    drawerState: DrawerState,
    viewModel: ShopListViewModel,
) {

    val viewState by viewModel.viewState.collectAsState()

    ShopListScreen(
        viewState = viewState,
        drawerState = drawerState,
        onLogoutClick = viewModel::onLogoutClick,
        onRefresh = viewModel::getData,
        onSorterChange = viewModel::onSorterChange,
        onFilterChange = viewModel::onFilterChange,
        onResetFilters = viewModel::onResetFilters,
        onShopItemClick = viewModel::onShopItemClick,
        onAddToCartClick = viewModel::addToCart,
        onToCartClick = viewModel::toCart,
        onToOrdersClick = viewModel::toOrders,
        onCloseDialogClick = viewModel::onCloseDialogClick,
        onUpdateQuantityClick = viewModel::onUpdateCartItemQuantityClick,
        onDeleteClick = viewModel::onDeleteCartItemClick,
    )
}
