package ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.model

import kotlinx.serialization.Serializable
import ru.kama_diesel.corp_portal_mobile.common.domain.model.CartItem
import ru.kama_diesel.corp_portal_mobile.common.domain.model.OrderItem
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ShopItem
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.Filter
import ru.kama_diesel.corp_portal_mobile.feature.shop.ui.screen.list.Sorter

@Serializable
data class ShopListViewState(
    val shopItems: List<ShopItem>,
    val cartItems: List<CartItem>,
    val orderItems: List<OrderItem>,
    val sortedShopItems: List<ShopItemUIModel>,
    val selectedSorter: Sorter,
    val selectedFilter: Filter,
    val balance: Int?,
    val dialog: ShopListDialog,
    val isLoading: Boolean,
)

@Serializable
sealed class ShopListDialog {
    @Serializable
    data object No : ShopListDialog()

    @Serializable
    data class Details(val shopItem: ShopItemUIModel) : ShopListDialog()
}

@Serializable
data class ShopItemUIModel(
    val id: Int,
    val name: String,
    val description: String,
    val characteristics: Map<String, String>,
    val partNumber: String?,
    val price: Int?,
    val imagePaths: List<String>?,
    val isAvailable: Boolean,
    val isActive: Boolean,
    val cartAddingState: CartAddingState,
)

@Serializable
sealed class CartAddingState {
    @Serializable
    data object No : CartAddingState()

    @Serializable
    data object Adding : CartAddingState()
}
