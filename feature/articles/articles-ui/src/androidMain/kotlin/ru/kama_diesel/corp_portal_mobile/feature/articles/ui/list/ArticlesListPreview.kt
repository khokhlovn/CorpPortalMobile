package ru.kama_diesel.corp_portal_mobile.feature.articles.ui.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.github.ahmad_hamwi.compose.pagination.rememberPaginationState
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ArticleItem
import ru.kama_diesel.corp_portal_mobile.common.ui.theme.AppTheme
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.ArticlesListScreen
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.ArticlesListScreenContent
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.model.ArticlesListDialog
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.model.ArticlesListViewState

@Preview
@Composable
private fun ArticlesListScreenPreview() {
    AppTheme {
        ArticlesListScreen(
            viewState = ArticlesListViewState(
                articleItems = listOf(),
                tagItems = listOf(),
                fromDate = null,
                toDate = null,
                dialog = ArticlesListDialog.No,
                isLoading = false,
                openedImagesPaths = listOf(),
                selectedImageIndex = 0,
            ),
            paginationState = rememberPaginationState<Int, ArticleItem>(initialPageKey = 1) { }.apply {
                allItems?.plus(articleItemsPreviewData)
            },
            onRefresh = {},
            onCheckedChange = { _, _ -> },
            onDateChange = { _, _ -> },
            onResetFilters = {},
            onArticleClick = { _, _, _, _, _, _, _ -> },
            onCloseDetailsClick = {},
            onCommentChange = { _ -> },
            onSendComment = {},
            onHideSnackbar = {},
            onLikeClick = {},
            onChangeRepliesVisibility = {},
            onReplyClick = {},
            onCancelReplyClick = {},
            onCommentLikeClick = {},
        )
    }
}

@Preview
@Composable
private fun ArticlesListScreenContentPreview() {
    AppTheme {
        ArticlesListScreenContent(
            paginationState = rememberPaginationState<Int, ArticleItem>(initialPageKey = 1) { }.apply {
                allItems?.plus(articleItemsPreviewData)
            },
            isRefreshing = false,
            onArticleClick = { _, _, _, _, _, _, _ -> },
        )
    }
}

val articleItemsPreviewData = listOf(
    ArticleItem(
        id = "",
        title = "План выполнен!",
        text = "В рамках годового собрания коллектива объявлено об успешном выполнении плана за 2023 год",
        imagePaths = null,
        tags = null,
        creationDate = "01.01.2025 12:00",
        isLiked = false,
        likesAmount = 1234,
    ),
)
