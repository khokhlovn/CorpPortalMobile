package ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.domain.usecase

import io.ktor.client.plugins.*
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
            val epochMilliseconds = Clock.System.now().toEpochMilliseconds()
            reservationListRepository.getReservationList(
                start = epochMilliseconds,
                finish = epochMilliseconds,
            )
        } catch (_: ClientRequestException) {
            logoutUseCase.invoke()
            listOf()
        } catch (_: Exception) {
            listOf()
        }
    }
}
