package ru.kama_diesel.corp_portal_mobile.feature.articles.ui.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ArticleItem
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.ArticlesListScreen
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.ArticlesListScreenContent
import ru.kama_diesel.corp_portal_mobile.feature.articles.ui.screen.list.model.ArticlesListViewState

@Preview(showBackground = true)
@Composable
private fun ArticlesListScreenPreview() {
    ArticlesListScreen(
        viewState = ArticlesListViewState(
            articleItems = listOf(),
            tagItems = listOf(),
            isLoading = false,
        ),
        onLogoutClick = {},
        onRefresh = {},
        onCheckedChange = { _, _ -> },
    )
}

@Preview(showBackground = true)
@Composable
private fun ArticlesListScreenContentPreview() {
    ArticlesListScreenContent(articleItems = articleItemsPreviewData, isRefreshing = false, onRefresh = {})
}

private val articleItemsPreviewData = listOf(
    ArticleItem(
        id = "",
        title = "План выполнен!",
        text = "В рамках годового собрания коллектива объявлено об успешном выполнении плана за 2023 год",
        imagePath = "",
    ),
)
