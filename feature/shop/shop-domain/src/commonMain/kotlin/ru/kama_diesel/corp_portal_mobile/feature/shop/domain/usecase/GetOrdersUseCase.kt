package ru.kama_diesel.corp_portal_mobile.feature.shop.domain.usecase

import io.ktor.client.plugins.*
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.ILogoutUseCase
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IShopRepository
import ru.kama_diesel.corp_portal_mobile.common.domain.model.OrderItem

@Inject
class GetOrdersUseCase(
    private val logoutUseCase: ILogoutUseCase,
    private val shopRepository: IShopRepository,
) {
    suspend operator fun invoke(): List<OrderItem> {
        return try {
            shopRepository.getOrders()
        } catch (_: ClientRequestException) {
            logoutUseCase.invoke()
            listOf()
        } catch (_: Exception) {
            listOf()
        }
    }
}
