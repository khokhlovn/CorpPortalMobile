package ru.kama_diesel.corp_portal_mobile.feature.shop.domain.usecase

import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IShopRepository
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ShopItem

@Inject
class GetShopListUseCase(
    private val shopRepository: IShopRepository,
) {
    suspend operator fun invoke(): List<ShopItem> {
        return try {
            shopRepository.getShopList()
        } catch (_: Exception) {
            listOf()
        }
    }
}
