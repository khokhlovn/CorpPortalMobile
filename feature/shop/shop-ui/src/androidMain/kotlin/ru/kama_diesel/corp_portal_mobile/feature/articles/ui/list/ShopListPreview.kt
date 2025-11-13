package ru.kama_diesel.corp_portal_mobile.feature.articles.ui.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ShopItem
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.ShopListScreen
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.ShopListScreenContent
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.model.ShopListDialog
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.model.ShopListViewState

@Preview(showBackground = true)
@Composable
private fun ShopListScreenPreview() {
    ShopListScreen(
        viewState = ShopListViewState(
            shopItems = listOf(),
            dialog = ShopListDialog.No,
            isLoading = false,
        ),
        onRefresh = {},
        onShopItemClick = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun ShopListScreenContentPreview() {
    ShopListScreenContent(
        shopItems = shopItemsPreviewData,
        isRefreshing = false,
        scrollEnabled = true,
        onRefresh = {},
        onShopItemClick = {},
    )
}

private val shopItemsPreviewData = listOf(
    ShopItem(
        id = 0,
        name = "Ежедневник в кожаной обложке",
        description = "",
        imagePaths = listOf(),
        partNumber = null,
        price = 1234,
        isAvailable = true,
        isActive = true,
    ),
)
