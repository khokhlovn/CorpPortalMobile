package ru.kama_diesel.corp_portal_mobile.feature.shop.domain.usecase

import io.ktor.client.plugins.*
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.ILogoutUseCase
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IShopRepository

@Inject
class CancelOrderUseCase(
    private val logoutUseCase: ILogoutUseCase,
    private val shopRepository: IShopRepository,
) {
    suspend operator fun invoke(cartId: Int) {
        return try {
            shopRepository.cancelOrder(cartId = cartId)
        } catch (_: ClientRequestException) {
            logoutUseCase.invoke()
        } catch (_: Exception) {
            return
        }
    }
}
