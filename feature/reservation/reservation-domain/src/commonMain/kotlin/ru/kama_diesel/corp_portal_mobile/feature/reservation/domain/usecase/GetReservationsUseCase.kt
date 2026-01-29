package ru.kama_diesel.corp_portal_mobile.feature.reservation.domain.usecase

import io.ktor.client.plugins.ClientRequestException
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.atTime
import kotlinx.datetime.toInstant
import kotlinx.datetime.todayIn
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.ILogoutUseCase
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IReservationListRepository
import ru.kama_diesel.corp_portal_mobile.common.domain.model.ReservationItem
import kotlin.time.Clock

@Inject
class GetReservationsUseCase(
    private val logoutUseCase: ILogoutUseCase,
    private val reservationListRepository: IReservationListRepository,
) {
    suspend operator fun invoke(fromDate: Long?, toDate: Long?): List<ReservationItem> {
        return try {
            reservationListRepository.getReservationList(
                start = fromDate,
                finish = toDate,
            )
        } catch (_: ClientRequestException) {
            logoutUseCase.invoke()
            listOf()
        } catch (_: Exception) {
            listOf()
        }
    }
}
