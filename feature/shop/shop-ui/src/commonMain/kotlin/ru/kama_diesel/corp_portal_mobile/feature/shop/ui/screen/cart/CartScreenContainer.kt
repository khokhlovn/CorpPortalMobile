package ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.cart

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun CartScreenContainer(
    viewModel: CartViewModel,
    wasNavigatedFromProfile: Boolean,
    onToProfileClick: () -> Unit,
) {

    val viewState by viewModel.viewState.collectAsState()

    CartScreen(
        viewState = viewState,
        onRefresh = viewModel::getData,
        onBackClick = if (wasNavigatedFromProfile) {
            onToProfileClick
        } else {
            viewModel::back
        },
        onUpdateQuantityClick = viewModel::onUpdateCartItemQuantityClick,
        onDeleteClick = viewModel::onDeleteCartItemClick,
        onOrderClick = viewModel::onMakeOrderClick,
        onCheckedChange = viewModel::onCartItemCheckedChange,
        onSelectAllClick = viewModel::onSelectAllClick,
        onDropSelectedItems = viewModel::onDropSelectedItems,
    )
}
