package ru.kama_diesel.corp_portal_mobile.di

import io.ktor.client.*
import io.ktor.client.plugins.cookies.*
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import me.tatarka.inject.annotations.Scope
import ru.kama_diesel.corp_portal_mobile.common.data.network.api.PreferencesCookieStorage
import ru.kama_diesel.corp_portal_mobile.common.data.preferences.IPreferences
import ru.kama_diesel.corp_portal_mobile.di.dependencies.RootComponentDependencies
import ru.kama_diesel.corp_portal_mobile.feature.root.component.api.IRootComponentDependencies

@Scope
annotation class AppScope

@AppScope
@Component
abstract class AppComponent(
    @Component val platformComponent: PlatformComponent,
) {

    abstract val rootComponentDependencies: IRootComponentDependencies

    @AppScope
    @Provides
    protected fun bind(it: RootComponentDependencies): IRootComponentDependencies = it

    @AppScope
    @Provides
    protected fun bind(it: PreferencesCookieStorage): CookiesStorage =
        PreferencesCookieStorage(platformComponent.preferences)

    companion object
}

abstract class PlatformComponent {
    abstract val preferences: IPreferences
    abstract val httpClient: HttpClient
}
