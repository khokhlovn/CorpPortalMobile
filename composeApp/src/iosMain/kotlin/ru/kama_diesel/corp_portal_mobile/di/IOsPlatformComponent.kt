package ru.kama_diesel.corp_portal_mobile.di

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

    companion object
}