package ru.kama_diesel.corp_portal_mobile.feature.main.component.di.dependencies

import io.ktor.client.*
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.data.preferences.IPreferences
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.ILogoutUseCase
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IReservationListRepository
import ru.kama_diesel.corp_portal_mobile.feature.top.component.api.ITopComponentDependencies

@Inject
internal class TopComponentDependencies(
    lazyLogoutUseCase: Lazy<ILogoutUseCase>,
    lazyPreferences: Lazy<IPreferences>,
    lazyReservationListRepository: Lazy<IReservationListRepository>,
    lazyHttpClient: Lazy<HttpClient>,
) : ITopComponentDependencies {
    override val logoutUseCase by lazyLogoutUseCase
    override val preferences by lazyPreferences
    override val reservationListRepository by lazyReservationListRepository
    override val httpClient by lazyHttpClient
}
