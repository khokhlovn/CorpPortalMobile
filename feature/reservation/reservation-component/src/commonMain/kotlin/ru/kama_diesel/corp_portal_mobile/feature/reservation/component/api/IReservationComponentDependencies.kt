package ru.kama_diesel.corp_portal_mobile.feature.reservation.component.api

import io.ktor.client.*
import ru.kama_diesel.corp_portal_mobile.common.data.preferences.IPreferences
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.ILogoutUseCase
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IReservationListRepository

interface IReservationComponentDependencies {
    val logoutUseCase: ILogoutUseCase
    val preferences: IPreferences
    val reservationListRepository: IReservationListRepository
    val httpClient: HttpClient
}
