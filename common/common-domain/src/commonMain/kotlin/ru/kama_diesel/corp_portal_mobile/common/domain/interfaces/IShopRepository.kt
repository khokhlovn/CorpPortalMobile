package ru.kama_diesel.corp_portal_mobile.common.domain.interfaces

import ru.kama_diesel.corp_portal_mobile.common.domain.model.CartItem
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ShopItem

interface IShopRepository {
    suspend fun getShopList(): List<ShopItem>
    suspend fun getCartData(): List<CartItem>
    suspend fun addToCart(itemId: Int, quantity: Int)
}
