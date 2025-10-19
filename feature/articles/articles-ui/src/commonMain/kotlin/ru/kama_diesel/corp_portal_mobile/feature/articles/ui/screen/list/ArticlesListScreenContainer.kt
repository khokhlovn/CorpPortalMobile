package ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun ArticlesListScreenContainer(viewModel: ArticlesListViewModel) {

    val viewState by viewModel.viewState.collectAsState()

    ArticlesListScreen(
        viewState = viewState,
        onLogoutClick = viewModel::onLogoutClick,
        onRefresh = viewModel::getData,
        onCheckedChange = viewModel::checkTag
    )
}
