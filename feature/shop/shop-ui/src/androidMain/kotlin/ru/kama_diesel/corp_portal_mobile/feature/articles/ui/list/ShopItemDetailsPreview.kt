package ru.kama_diesel.corp_portal_mobile.feature.articles.ui.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.kama_diesel.corp_portal_mobile.common.ui.theme.AppTheme
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.details.ShopItemDetailsDialog
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.model.CartAddingState
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.model.ShopItemUIModel

@Preview
@Composable
private fun ShopItemDetailsPreview() {
    AppTheme {
        ShopItemDetailsDialog(
            shopItem = ShopItemUIModel(
                id = 0,
                name = "Ежедневник в кожаной обложке",
                description = "",
                characteristics = emptyMap(),
                imagePaths = listOf(""),
                partNumber = null,
                price = 1234,
                isAvailable = true,
                isActive = true,
                quantity = 12,
                cartAddingState = CartAddingState.No,
            ),
            quantity = 0,
            cartAddingState = CartAddingState.No,
            onCloseClick = {},
            onAddToCartClick = {},
            onAddClick = {},
            onRemoveClick = {},
            onDeleteClick = {},
        )
    }
}
