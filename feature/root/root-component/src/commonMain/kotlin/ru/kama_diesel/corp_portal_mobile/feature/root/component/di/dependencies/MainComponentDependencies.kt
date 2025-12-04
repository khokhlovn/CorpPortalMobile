package ru.kama_diesel.corp_portal_mobile.feature.root.component.di.dependencies

import io.ktor.client.*
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.data.preferences.IPreferences
import ru.kama_diesel.corp_portal_mobile.common.data.repository.ReservationListRepository
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.ILogoutUseCase
import ru.kama_diesel.corp_portal_mobile.feature.main.component.api.IMainComponentDependencies

@Inject
internal class MainComponentDependencies(
    lazyPreferences: Lazy<IPreferences>,
    lazyLogoutUseCase: Lazy<ILogoutUseCase>,
    lazyReservationListRepository: Lazy<ReservationListRepository>,
    lazyHttpClient: Lazy<HttpClient>,
) : IMainComponentDependencies {
    override val httpClient by lazyHttpClient
    override val logoutUseCase by lazyLogoutUseCase
    override val reservationListRepository by lazyReservationListRepository
    override val preferences by lazyPreferences
}
