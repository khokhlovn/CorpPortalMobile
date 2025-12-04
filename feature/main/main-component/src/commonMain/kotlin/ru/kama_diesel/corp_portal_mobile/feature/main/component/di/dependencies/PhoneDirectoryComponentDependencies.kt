package ru.kama_diesel.corp_portal_mobile.feature.main.component.di.dependencies

import io.ktor.client.*
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.data.preferences.IPreferences
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.ILogoutUseCase
import ru.kama_diesel.corp_portal_mobile.common.domain.interfaces.IReservationListRepository
import ru.kama_diesel.corp_portal_mobile.feature.phoneDirectory.component.api.IPhoneDirectoryComponentDependencies

@Inject
internal class PhoneDirectoryComponentDependencies(
    lazyLogoutUseCase: Lazy<ILogoutUseCase>,
    lazyPreferences: Lazy<IPreferences>,
    lazyReservationListRepository: Lazy<IReservationListRepository>,
    lazyHttpClient: Lazy<HttpClient>,
) : IPhoneDirectoryComponentDependencies {
    override val logoutUseCase by lazyLogoutUseCase
    override val preferences by lazyPreferences
    override val reservationListRepository by lazyReservationListRepository
    override val httpClient by lazyHttpClient
}
