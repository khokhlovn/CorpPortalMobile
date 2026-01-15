package ru.kama_diesel.corp_portal_mobile.feature.main.component.di.dependencies

import io.ktor.client.HttpClient
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.data.preferences.IPreferences
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.ILogoutUseCase
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IReservationListRepository
import ru.kama_diesel.corp_portal_mobile.feature.reservation.component.api.IReservationComponentDependencies
import ru.kama_diesel.corp_portal_mobile.feature.top.component.api.ITopComponentDependencies
import kotlin.getValue

@Inject
internal class ReservationComponentDependencies(
    lazyLogoutUseCase: Lazy<ILogoutUseCase>,
    lazyPreferences: Lazy<IPreferences>,
    lazyReservationListRepository: Lazy<IReservationListRepository>,
    lazyHttpClient: Lazy<HttpClient>,
) : IReservationComponentDependencies {
    override val logoutUseCase by lazyLogoutUseCase
    override val preferences by lazyPreferences
    override val reservationListRepository by lazyReservationListRepository
    override val httpClient by lazyHttpClient
}
