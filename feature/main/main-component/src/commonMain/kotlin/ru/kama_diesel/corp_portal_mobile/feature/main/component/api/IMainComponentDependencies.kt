package ru.kama_diesel.corp_portal_mobile.feature.main.component.api

import io.ktor.client.*
import ru.kama_diesel.corp_portal_mobile.common.data.preferences.IPreferences
import ru.kama_diesel.corp_portal_mobile.feature.articles.domain.api.ILogoutUseCase

interface IMainComponentDependencies {
    val preferences: IPreferences
    val httpClient: HttpClient
    val logoutUseCase: ILogoutUseCase
}