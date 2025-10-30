package ru.kama_diesel.corp_portal_mobile.feature.root.component.api

import io.ktor.client.*
import ru.kama_diesel.corp_portal_mobile.common.data.preferences.IPreferences

interface IRootComponentDependencies {
    val preferences: IPreferences
    val httpClient: HttpClient
}