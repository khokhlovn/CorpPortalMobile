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
        onShopItemClick = viewModel::onShopItemClick,
    )
}
