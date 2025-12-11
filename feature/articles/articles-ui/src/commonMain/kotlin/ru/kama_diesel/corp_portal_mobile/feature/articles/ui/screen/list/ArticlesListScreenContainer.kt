package ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun ArticlesListScreenContainer(viewModel: ArticlesListViewModel) {

    val viewState by viewModel.viewState.collectAsState()

    ArticlesListScreen(
        viewState = viewState,
        paginationState = viewModel.paginationState,
        onRefresh = { viewModel.getData(needListRefresh = true) },
        onCheckedChange = viewModel::checkTag,
        onDateChange = viewModel::onDateChange,
        onResetFilters = viewModel::onResetFilters,
        onArticleClick = viewModel::onArticleClick,
        onCloseDetailsClick = viewModel::onCloseDetailsClick,
        onCommentChange = viewModel::onCommentChange,
        onSendComment = viewModel::onSendComment,
        onHideSnackbar = viewModel::onHideCommentSentSnackbar,
        onLikeClick = viewModel::onLikeClick,
        onChangeRepliesVisibility = viewModel::onChangeRepliesVisibility,
        onReplyClick = viewModel::onReplyClick,
        onCancelReplyClick = viewModel::onCancelReplyClick,
    )
}
