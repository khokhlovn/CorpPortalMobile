package ru.kama_diesel.corp_portal_mobile.feature.top.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun TopScreenContainer(
    viewModel: TopViewModel,
) {
    val viewState by viewModel.viewState.collectAsState()

    TopScreen(
        viewState = viewState,
        onRefresh = viewModel::getData,
    )
}
