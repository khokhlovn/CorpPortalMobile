package ru.kama_diesel.corp_portal_mobile.feature.articles.ui.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ShopItem
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.details.ShopItemDetailsDialog

@Preview(showBackground = true)
@Composable
private fun ShopItemDetailsPreview() {
    ShopItemDetailsDialog(
        shopItem = ShopItem(
            id = 0,
            name = "Ежедневник в кожаной обложке",
            description = "",
            characteristics = emptyMap(),
            imagePaths = listOf(""),
            partNumber = null,
            price = 1234,
            isAvailable = true,
            isActive = true,
        ),
        onCloseClick = {},
        onAddToCartClick = {},
    )
}
