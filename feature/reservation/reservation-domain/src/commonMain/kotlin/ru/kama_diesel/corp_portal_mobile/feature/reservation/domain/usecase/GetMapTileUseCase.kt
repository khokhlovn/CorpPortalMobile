package ru.kama_diesel.corp_portal_mobile.feature.reservation.domain.usecase

import io.ktor.client.plugins.*
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.ILogoutUseCase
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IReservationRepository
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.ITopRepository
import ru.kama_diesel.corp_portal_mobile.common.domain.model.TopWorkerItem

@Inject
class
GetMapTileUseCase(
    private val logoutUseCase: ILogoutUseCase,
    private val reservationRepository: IReservationRepository,
) {
    suspend operator fun invoke(row: Int, col: Int, zoomLvl: Int): ByteArray {
        return try {
            reservationRepository.getMap(
                row = row,
                col = col,
                zoomLvl = zoomLvl,
            )
        } catch (_: ClientRequestException) {
            logoutUseCase.invoke()
            byteArrayOf(Byte.MIN_VALUE)
        } catch (_: Exception) {
            byteArrayOf(Byte.MIN_VALUE)
        }
    }
}
