package ru.kama_diesel.corp_portal_mobile.di

import android.content.Context
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import ru.kama_diesel.corp_portal_mobile.common.data.preferences.IPreferences

@AppScope
@Component
abstract class AndroidPlatformComponent(private val applicationContext: Context) : PlatformComponent() {

    val providesApplicationContext: Context
        @AppScope @Provides get() = applicationContext

    @AppScope
    @Provides
    fun providesPreferences(applicationContext: Context): IPreferences = getPreferences(applicationContext)
}
