package ru.kama_diesel.corp_portal_mobile.feature.main.ui.screen

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable

@Composable
fun MainScreenContainer(
    drawerState: DrawerState,
    selectedIndex: Int,
    tab: @Composable () -> Unit,
    viewModel: MainViewModel,
) {
    MainScreen(
        drawerState = drawerState,
        selectedIndex = selectedIndex,
        tab = tab,
        onArticlesClick = viewModel::onArticlesClick,
        onShopClick = viewModel::onShopClick,
        onPhoneDirectoryClick = viewModel::onPhoneDirectoryClick,
        onLogoutClick = viewModel::onLogoutClick,
    )
}
