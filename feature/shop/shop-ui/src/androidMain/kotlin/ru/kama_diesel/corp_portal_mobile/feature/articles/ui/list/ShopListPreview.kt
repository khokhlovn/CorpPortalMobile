package ru.kama_diesel.corp_portal_mobile.feature.articles.ui.list

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.kama_diesel.corp_portal_mobile.common.domain.model.CartItem
import ru.kama_diesel.corp_portal_mobile.common.ui.theme.AppTheme
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.Filter
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.ShopListScreen
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.ShopListScreenContent
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.Sorter
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.model.CartAddingState
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.model.ShopItemUIModel
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.model.ShopListDialog
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.model.ShopListViewState

@Preview
@Composable
private fun ShopListScreenPreview() {
    AppTheme {
        ShopListScreen(
            viewState = ShopListViewState(
                shopItems = listOf(),
                cartItems = listOf(),
                orderItems = listOf(),
                sortedShopItems = listOf(),
                selectedSorter = Sorter.PriceIncreasing,
                selectedFilter = Filter.All,
                balance = 0,
                dialog = ShopListDialog.No,
                isLoading = false,
            ),
            drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
            onLogoutClick = {},
            onRefresh = {},
            onSorterChange = {},
            onFilterChange = {},
            onResetFilters = {},
            onShopItemClick = {},
            onAddToCartClick = {},
            onToCartClick = {},
            onToOrdersClick = {},
            onCloseDialogClick = {},
            onUpdateQuantityClick = { _, _ -> },
            onDeleteClick = {},
        )
    }
}

@Preview
@Composable
private fun ShopListScreenContentPreview() {
    AppTheme {
        ShopListScreenContent(
            shopItems = shopItemsPreviewData,
            cartItems = listOf(CartItem(inCartItemId = 0, itemId = 0, quantity = 1)),
            orderItems = listOf(),
            selectedSorter = Sorter.PriceIncreasing,
            selectedFilter = Filter.All,
            isRefreshing = false,
            onRefresh = {},
            onSorterChange = {},
            onFilterChange = {},
            onResetFilters = {},
            onShopItemClick = {},
            onToCartClick = {},
            onToOrdersClick = {},
            onAddToCartClick = {},
            onUpdateQuantityClick = { _, _ -> },
            onDeleteClick = {},
        )
    }
}

private val shopItemsPreviewData = listOf(
    ShopItemUIModel(
        id = 0,
        name = "Ежедневник в кожаной обложке",
        description = "",
        characteristics = emptyMap(),
        imagePaths = listOf(),
        partNumber = null,
        price = 1234,
        isAvailable = true,
        isActive = true,
        cartAddingState = CartAddingState.No,
    ),
    ShopItemUIModel(
        id = 1,
        name = "Ежедневник в кожаной обложке",
        description = "",
        characteristics = emptyMap(),
        imagePaths = listOf(),
        partNumber = null,
        price = 1234,
        isAvailable = true,
        isActive = true,
        cartAddingState = CartAddingState.No,
    ),
    ShopItemUIModel(
        id = 2,
        name = "Ежедневник в кожаной обложке",
        description = "",
        characteristics = emptyMap(),
        imagePaths = listOf(),
        partNumber = null,
        price = 1234,
        isAvailable = true,
        isActive = true,
        cartAddingState = CartAddingState.Adding,
    ),
)
