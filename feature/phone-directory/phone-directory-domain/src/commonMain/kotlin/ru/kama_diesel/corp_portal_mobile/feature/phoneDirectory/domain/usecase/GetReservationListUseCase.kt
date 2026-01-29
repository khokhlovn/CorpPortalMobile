package ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.domain.usecase

import io.ktor.client.plugins.*
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
import kotlin.time.ExperimentalTime

@Inject
class GetReservationListUseCase(
    private val logoutUseCase: ILogoutUseCase,
    private val reservationListRepository: IReservationListRepository,
) {
    @OptIn(ExperimentalTime::class)
    suspend operator fun invoke(): List<ReservationItem> {
        return try {
            reservationListRepository.getReservationList(
                start = Clock.System.todayIn(TimeZone.currentSystemDefault()).atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds(),
                finish = Clock.System.todayIn(TimeZone.currentSystemDefault()).atTime(23, 59, 59).toInstant(TimeZone.currentSystemDefault())
                    .toEpochMilliseconds(),
            )
        } catch (_: ClientRequestException) {
            logoutUseCase.invoke()
            listOf()
        } catch (_: Exception) {
            listOf()
        }
    }
}
