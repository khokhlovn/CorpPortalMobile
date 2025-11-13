package ru.kama_diesel.corp_portal_mobile.feature.main.ui.screen

import androidx.compose.runtime.Composable

@Composable
fun MainScreenContainer(
    selectedIndex: Int,
    tab: @Composable () -> Unit,
    viewModel: MainViewModel,
) {
    MainScreen(
        selectedIndex = selectedIndex,
        tab = tab,
        onArticlesClick = viewModel::onArticlesClick,
        onShopClick = viewModel::onShopClick,
    )
}
