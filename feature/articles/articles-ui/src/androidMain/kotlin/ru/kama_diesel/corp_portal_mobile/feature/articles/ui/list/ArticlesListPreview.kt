package ru.kama_diesel.corp_portal_mobile.feature.articles.ui.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ArticleItem
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.ArticlesListScreen
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.ArticlesListScreenContent
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.model.ArticlesListDialog
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.model.ArticlesListViewState

@Preview(showBackground = true)
@Composable
private fun ArticlesListScreenPreview() {
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
    )
}

@Preview(showBackground = true)
@Composable
private fun ArticlesListScreenContentPreview() {
    ArticlesListScreenContent(
        articleItems = articleItemsPreviewData,
        isRefreshing = false,
        scrollEnabled = true,
        onRefresh = {},
        onArticleClick = { _, _, _, _, _, _, _ -> },
    )
}

private val articleItemsPreviewData = listOf(
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
