package ru.kama_diesel.corp_portal_mobile.feature.shop.domain.usecase

import io.ktor.client.plugins.ClientRequestException
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.ILogoutUseCase
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IShopRepository
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ShopItem

@Inject
class GetAllShopListUseCase(
    private val logoutUseCase: ILogoutUseCase,
    private val shopRepository: IShopRepository,
) {
    suspend operator fun invoke(): List<ShopItem> {
        return try {
            shopRepository.getAllShopList()
        } catch (_: ClientRequestException) {
            logoutUseCase.invoke()
            listOf()
        } catch (_: Exception) {
            listOf()
        }
    }
}
