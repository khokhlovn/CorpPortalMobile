package ru.kama_diesel.corp_portal_mobile.feature.shop.domain.usecase

import io.ktor.client.plugins.*
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.ILogoutUseCase
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IShopRepository

@Inject
class AddToCartUseCase(
    private val logoutUseCase: ILogoutUseCase,
    private val shopRepository: IShopRepository,
) {
    suspend operator fun invoke(itemId: Int, quantity: Int) {
        return try {
            shopRepository.addToCart(itemId = itemId, quantity = quantity)
        } catch (_: ClientRequestException) {
            logoutUseCase.invoke()
        }
    }
}
