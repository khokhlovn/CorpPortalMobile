package ru.kama_diesel.corp_portal_mobile.feature.shop.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.data.network.api.CorpPortalApi
import ru.kama_diesel.corp_portal_mobile.common.data.network.mapper.DateTimeMapper
import ru.kama_diesel.corp_portal_mobile.common.data.network.model.AddToCartRequestData
import ru.kama_diesel.corp_portal_mobile.common.data.network.model.CancelOrderRequestData
import ru.kama_diesel.corp_portal_mobile.common.data.network.model.DropCartItemRequestData
import ru.kama_diesel.corp_portal_mobile.common.data.network.model.UpdateCartItemRequestData
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IShopRepository
import ru.kama_diesel.corp_portal_mobile.common.domain.model.CartItem
import ru.kama_diesel.corp_portal_mobile.common.domain.model.OrderItem
import ru.kama_diesel.corp_portal_mobile.common.domain.model.OrderPositionItem
import ru.kama_diesel.corp_portal_mobile.common.domain.model.OrderStatus
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ShopItem

@Inject
class ShopRepository(
    private val corpPortalApi: CorpPortalApi,
) : IShopRepository {

    override suspend fun getShopList(): List<ShopItem> {
        return withContext(Dispatchers.IO) {
            corpPortalApi.getShopList().shopItems?.filter { it.isAvailable == true }?.map {
                ShopItem(
                    id = it.itemId,
                    name = it.name,
                    description = it.description,
                    characteristics = Json.decodeFromString<Map<String, String>>(it.characteristics.toString()),
                    partNumber = it.partNumber,
                    price = it.price,
                    imagePaths = it.photoPaths,
                    isAvailable = it.isAvailable ?: false,
                    isActive = it.isActive ?: false,
                    quantity = it.quantity,
                )
            } ?: listOf()
        }
    }

    override suspend fun getAllShopList(): List<ShopItem> {
        return withContext(Dispatchers.IO) {
            corpPortalApi.getShopList().shopItems?.map {
                ShopItem(
                    id = it.itemId,
                    name = it.name,
                    description = it.description,
                    characteristics = Json.decodeFromString<Map<String, String>>(it.characteristics.toString()),
                    partNumber = it.partNumber,
                    price = it.price,
                    imagePaths = it.photoPaths,
                    isAvailable = it.isAvailable ?: false,
                    isActive = it.isActive ?: false,
                    quantity = it.quantity,
                )
            } ?: listOf()
        }
    }

    override suspend fun getCartData(): List<CartItem> {
        return withContext(Dispatchers.IO) {
            corpPortalApi.getCartData().cartItems?.sortedBy { it.inCartItemId }?.map {
                CartItem(
                    inCartItemId = it.inCartItemId,
                    itemId = it.itemId,
                    quantity = it.quantity,
                )
            } ?: listOf()
        }
    }

    override suspend fun addToCart(itemId: Int, quantity: Int) {
        return withContext(Dispatchers.IO) {
            corpPortalApi.addToCart(
                addToCartRequestData = AddToCartRequestData(
                    itemId = itemId,
                    quantity = quantity,
                )
            )
        }
    }

    override suspend fun updateCartItem(inCartItemId: Int, quantity: Int) {
        return withContext(Dispatchers.IO) {
            corpPortalApi.updateCartItem(
                updateCartItemRequestData = UpdateCartItemRequestData(
                    inCartItemId = inCartItemId,
                    quantity = quantity,
                )
            )
        }
    }

    override suspend fun dropCartItem(inCartItemId: Int) {
        return withContext(Dispatchers.IO) {
            corpPortalApi.dropCartItem(
                dropCartItemRequestData = DropCartItemRequestData(
                    inCartItemId = inCartItemId,
                )
            )
        }
    }

    override suspend fun getBalance(): Int {
        return withContext(Dispatchers.IO) {
            corpPortalApi.getMyInfo().user.balance
        }
    }

    override suspend fun makeOrder() {
        return withContext(Dispatchers.IO) {
            corpPortalApi.makeOrder()
        }
    }

    override suspend fun getOrders(): List<OrderItem> {
        return withContext(Dispatchers.IO) {
            corpPortalApi.getOrders().carts?.map { cart ->
                OrderItem(
                    id = cart.cartId,
                    date = DateTimeMapper.getFormattedDate(
                        iso8601Timestamp = cart.date,
                        format = "dd.MM.yyyy",
                    ),
                    status = OrderStatus.entries.find { it.value == cart.status } ?: OrderStatus.Ordered,
                    items = cart.items.map { item ->
                        OrderPositionItem(
                            id = item.itemId,
                            quantity = item.quantity,
                        )
                    }
                )
            } ?: listOf()
        }
    }

    override suspend fun cancelOrder(cartId: Int) {
        return withContext(Dispatchers.IO) {
            corpPortalApi.cancelOrder(
                cancelOrderRequestData = CancelOrderRequestData(
                    cartId = cartId,
                )
            )
        }
    }
}
