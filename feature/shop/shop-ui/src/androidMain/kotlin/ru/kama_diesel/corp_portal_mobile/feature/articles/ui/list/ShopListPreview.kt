package ru.kama_diesel.corp_portal_mobile.feature.articles.ui.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.kama_diesel.corp_portal_mobile.common.domain.model.CartItem
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ShopItem
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.Filter
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.ShopListScreen
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.ShopListScreenContent
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.Sorter
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.model.CartAddingState
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.model.ShopListDialog
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.model.ShopListViewState

@Preview(showBackground = true)
@Composable
private fun ShopListScreenPreview() {
    ShopListScreen(
        viewState = ShopListViewState(
            shopItems = listOf(),
            cartItems = listOf(),
            sortedShopItems = listOf(),
            selectedSorter = Sorter.PriceIncreasing,
            selectedFilter = Filter.All,
            dialog = ShopListDialog.No,
            cartAddingState = CartAddingState.No,
            isLoading = false,
        ),
        onRefresh = {},
        onSorterChange = {},
        onFilterChange = {},
        onResetFilters = {},
        onShopItemClick = {},
        onAddToCartClick = {},
        onToCartClick = {},
        onCloseDialogClick = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun ShopListScreenContentPreview() {
    ShopListScreenContent(
        shopItems = shopItemsPreviewData,
        cartItems = listOf(CartItem(inCartItemId = 0, itemId = 0, quantity = 1)),
        selectedSorter = Sorter.PriceIncreasing,
        selectedFilter = Filter.All,
        isRefreshing = false,
        cartAddingState = CartAddingState.No,
        onRefresh = {},
        onSorterChange = {},
        onFilterChange = {},
        onResetFilters = {},
        onShopItemClick = {},
        onToCartClick = {},
        onAddToCartClick = {},
    )
}

private val shopItemsPreviewData = listOf(
    ShopItem(
        id = 0,
        name = "Ежедневник в кожаной обложке",
        description = "",
        characteristics = emptyMap(),
        imagePaths = listOf(),
        partNumber = null,
        price = 1234,
        isAvailable = true,
        isActive = true,
    ),
)
