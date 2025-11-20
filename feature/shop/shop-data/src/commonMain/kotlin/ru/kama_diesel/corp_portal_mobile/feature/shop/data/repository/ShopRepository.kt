package ru.kama_diesel.corp_portal_mobile.feature.shop.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.data.network.api.CorpPortalApi
import ru.kama_diesel.corp_portal_mobile.common.data.network.model.AddToCartRequestData
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IShopRepository
import ru.kama_diesel.corp_portal_mobile.common.domain.model.CartItem
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ShopItem

@Inject
class ShopRepository(
    private val corpPortalApi: CorpPortalApi,
) : IShopRepository {

    override suspend fun getShopList(): List<ShopItem> {
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
                )
            } ?: listOf()
        }
    }

    override suspend fun getCartData(): List<CartItem> {
        return withContext(Dispatchers.IO) {
            corpPortalApi.getCartData().cartItems?.map {
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
}
