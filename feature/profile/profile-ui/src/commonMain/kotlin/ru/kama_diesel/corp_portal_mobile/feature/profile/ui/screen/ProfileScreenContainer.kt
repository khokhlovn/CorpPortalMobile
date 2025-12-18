package ru.kama_diesel.corp_portal_mobile.feature.profile.ui.screen

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun ProfileScreenContainer(
    drawerState: DrawerState,
    viewModel: ProfileViewModel,
) {
    val viewState by viewModel.viewState.collectAsState()

    ProfileScreen(
        drawerState = drawerState,
        viewState = viewState,
        onRefresh = viewModel::getData,
        onLogoutClick = viewModel::onLogoutClick,
    )
}
