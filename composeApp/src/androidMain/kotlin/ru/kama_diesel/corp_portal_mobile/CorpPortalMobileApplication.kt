package ru.kama_diesel.corp_portal_mobile

import android.app.Application
import ru.kama_diesel.corp_portal_mobile.di.AndroidPlatformComponent
import ru.kama_diesel.corp_portal_mobile.di.AppComponent
import ru.kama_diesel.corp_portal_mobile.di.create

class CorpPortalMobileApplication : Application() {
    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        val platformComponent = AndroidPlatformComponent::class.create(this.applicationContext)
        appComponent = AppComponent::class.create(platformComponent)
    }

    fun appComponent(): AppComponent = appComponent
}
