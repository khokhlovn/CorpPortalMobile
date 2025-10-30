package ru.kama_diesel.corp_portal_mobile.di.dependencies

import io.ktor.client.*
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.data.preferences.IPreferences
import ru.kama_diesel.corp_portal_mobile.feature.root.component.api.IRootComponentDependencies

@Inject
class RootComponentDependencies(
    lazyPreferences: Lazy<IPreferences>,
    lazyHttpClient: Lazy<HttpClient>,
) : IRootComponentDependencies {
    override val preferences by lazyPreferences
    override val httpClient by lazyHttpClient
}
