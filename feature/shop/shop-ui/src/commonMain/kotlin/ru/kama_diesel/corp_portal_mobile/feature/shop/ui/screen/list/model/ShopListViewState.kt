package ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.model

import kotlinx.serialization.Serializable
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ShopItem

@Serializable
data class ShopListViewState(
    val shopItems: List<ShopItem>,
    val dialog: ShopListDialog,
    val isLoading: Boolean,
)

@Serializable
sealed class ShopListDialog {
    @Serializable
    data object No : ShopListDialog()

    @Serializable
    data object Loading : ShopListDialog()
}
