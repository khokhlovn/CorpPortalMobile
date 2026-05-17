package ru.kama_diesel.corp_portal_mobile.feature.top.ui.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import ru.kama_diesel.corp_portal_mobile.feature.top.ui.screen.model.TopViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopScreen(
    viewState: TopViewState,
    onRefresh: () -> Unit,
) {
    TopScreenContent(
        wallOfFameItems = viewState.wallOfFameItems,
        isRefreshing = viewState.isLoading,
        onRefresh = onRefresh,
    )
}
