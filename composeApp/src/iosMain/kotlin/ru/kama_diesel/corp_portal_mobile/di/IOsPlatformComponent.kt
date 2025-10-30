package ru.kama_diesel.corp_portal_mobile.di

import io.ktor.client.*
import io.ktor.client.plugins.cookies.*
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import platform.Foundation.NSUserDefaults
import ru.kama_diesel.corp_portal_mobile.common.data.preferences.IPreferences


@AppScope
@Component
abstract class IOsPlatformComponent(private val userDefaults: NSUserDefaults) : PlatformComponent() {
    @AppScope
    @Provides
    fun providesPreferences(): IPreferences = getPreferences(userDefaults)

    @AppScope
    @Provides
    fun providesHttpClient(preferencesCookieStorage: CookiesStorage): HttpClient =
        getHttpClient(preferencesCookieStorage)

    companion object
}