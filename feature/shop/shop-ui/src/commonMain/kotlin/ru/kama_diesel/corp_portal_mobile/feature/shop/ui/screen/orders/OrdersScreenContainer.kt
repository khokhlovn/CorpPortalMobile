package ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.orders

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun OrdersScreenContainer(viewModel: OrdersViewModel) {

    val viewState by viewModel.viewState.collectAsState()

    OrdersScreen(
        viewState = viewState,
        onRefresh = viewModel::getData,
        onBackClick = viewModel::back,
        onSorterChange = viewModel::onSorterChange,
    )
}
