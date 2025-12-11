package ru.kama_diesel.corp_portal_mobile.common.domain.interfaces

import ru.kama_diesel.corp_portal_mobile.common.domain.model.CartItem
import ru.kama_diesel.corp_portal_mobile.common.domain.model.OrderItem
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ShopItem

interface IShopRepository {
    suspend fun getShopList(): List<ShopItem>
    suspend fun getCartData(): List<CartItem>
    suspend fun addToCart(itemId: Int, quantity: Int)
    suspend fun updateCartItem(inCartItemId: Int, quantity: Int)
    suspend fun dropCartItem(inCartItemId: Int)
    suspend fun getBalance(): Int
    suspend fun makeOrder()
    suspend fun getOrders(): List<OrderItem>
    suspend fun cancelOrder(cartId: Int)
}
