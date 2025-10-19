package ru.kama_diesel.corp_portal_mobile

import android.app.Application
import io.appmetrica.analytics.AppMetrica
import io.appmetrica.analytics.AppMetricaConfig
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import ru.kama_diesel.corp_portal_mobile.di.AndroidPlatformComponent
import ru.kama_diesel.corp_portal_mobile.di.AppComponent
import ru.kama_diesel.corp_portal_mobile.di.create

class CorpPortalMobileApplication : Application() {
    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        val config = AppMetricaConfig.newConfigBuilder(BuildKonfig.APPMETRICA_KEY)
            .withLogs()
            .build()

        AppMetrica.activate(this, config)
        Napier.base(DebugAntilog())

        val platformComponent = AndroidPlatformComponent::class.create(this.applicationContext)
        appComponent = AppComponent::class.create(platformComponent)
    }

    fun appComponent(): AppComponent = appComponent
}
