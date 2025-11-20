package ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun ShopListScreenContainer(viewModel: ShopListViewModel) {

    val viewState by viewModel.viewState.collectAsState()

    ShopListScreen(
        viewState = viewState,
        onRefresh = viewModel::getData,
        onSorterChange = viewModel::onSorterChange,
        onFilterChange = viewModel::onFilterChange,
        onResetFilters = viewModel::onResetFilters,
        onShopItemClick = viewModel::onShopItemClick,
        onAddToCartClick = viewModel::addToCart,
        onToCartClick = viewModel::toCart,
        onCloseDialogClick = viewModel::onCloseDialogClick,
    )
}
