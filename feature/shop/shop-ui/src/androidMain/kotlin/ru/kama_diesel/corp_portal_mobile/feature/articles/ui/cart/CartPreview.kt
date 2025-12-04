package ru.kama_diesel.corp_portal_mobile.feature.articles.ui.cart

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ShopItem
import ru.kama_diesel.corp_portal_mobile.common.ui.theme.AppTheme
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.cart.CartScreen
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.cart.CartScreenContent
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.cart.model.CartAddingState
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.cart.model.CartItemUIModel
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.cart.model.CartViewState
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.cart.model.MakingOrderState

@Preview
@Composable
private fun CartScreenPreview() {
    AppTheme {
        CartScreen(
            viewState = CartViewState(
                shopItems = listOf(),
                cartItems = listOf(),
                totalSum = 9999,
                balance = 0,
                isLoading = false,
                makingOrderState = MakingOrderState.No,
            ),
            onRefresh = {},
            onBackClick = {},
            onUpdateQuantityClick = { _, _ -> },
            onDeleteClick = {},
            onOrderClick = {},
            onCheckedChange = { _, _ -> },
            onSelectAllClick = {},
            onDropSelectedItems = {},
        )
    }
}

@Preview
@Composable
private fun CartScreenContentPreview() {
    AppTheme {
        CartScreenContent(
            shopItems = shopItemsPreviewData,
            cartItems = listOf(
                CartItemUIModel(
                    inCartItemId = 0,
                    itemId = 0,
                    quantity = 1,
                    isChecked = true,
                    cartAddingState = CartAddingState.No,
                )
            ),
            isRefreshing = false,
            onRefresh = {},
            onUpdateQuantityClick = { _, _ -> },
            balance = 0,
            totalSum = 9999,
            makingOrderState = MakingOrderState.No,
            onDeleteClick = {},
            onOrderClick = {},
            onCheckedChange = { _, _ -> },
            onSelectAllClick = {},
            onDropSelectedItems = {},
        )
    }
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
    ShopItem(
        id = 1,
        name = "Ежедневник в кожаной обложке",
        description = "",
        characteristics = emptyMap(),
        imagePaths = listOf(),
        partNumber = null,
        price = 1234,
        isAvailable = true,
        isActive = true,
    ),
    ShopItem(
        id = 2,
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
