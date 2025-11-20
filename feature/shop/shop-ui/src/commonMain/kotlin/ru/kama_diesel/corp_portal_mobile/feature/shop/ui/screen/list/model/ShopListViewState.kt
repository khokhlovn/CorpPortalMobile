package ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.model

import kotlinx.serialization.Serializable
import ru.kama_diesel.corp_portal_mobile.common.domain.model.CartItem
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ShopItem
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.Filter
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.Sorter

@Serializable
data class ShopListViewState(
    val shopItems: List<ShopItem>,
    val cartItems: List<CartItem>,
    val sortedShopItems: List<ShopItem>,
    val selectedSorter: Sorter,
    val selectedFilter: Filter,
    val dialog: ShopListDialog,
    val cartAddingState: CartAddingState,
    val isLoading: Boolean,
)

@Serializable
sealed class ShopListDialog {
    @Serializable
    data object No : ShopListDialog()

    @Serializable
    data object Loading : ShopListDialog()

    @Serializable
    data class Details(val shopItem: ShopItem) : ShopListDialog()
}

@Serializable
sealed class CartAddingState {
    @Serializable
    data object No : CartAddingState()

    @Serializable
    data object Adding : CartAddingState()

    @Serializable
    data object Success : CartAddingState()
}
