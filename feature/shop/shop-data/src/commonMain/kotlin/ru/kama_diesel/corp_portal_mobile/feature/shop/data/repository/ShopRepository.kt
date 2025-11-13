package ru.kama_diesel.corp_portal_mobile.feature.shop.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.data.network.api.CorpPortalApi
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IShopRepository
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
                    partNumber = it.partNumber,
                    price = it.price,
                    imagePaths = it.photoPaths,
                    isAvailable = it.isAvailable,
                    isActive = it.isActive,
                )
            } ?: listOf()
        }
    }
}
