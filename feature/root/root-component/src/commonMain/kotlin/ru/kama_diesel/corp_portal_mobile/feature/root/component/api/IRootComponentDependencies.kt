package ru.kama_diesel.corp_portal_mobile.feature.root.component.api

import ru.kama_diesel.corp_portal_mobile.common.data.network.api.CorpPortalApi
import ru.kama_diesel.corp_portal_mobile.common.data.preferences.IPreferences

interface IRootComponentDependencies {
    val preferences: IPreferences
    val corpPortalApi: CorpPortalApi
}