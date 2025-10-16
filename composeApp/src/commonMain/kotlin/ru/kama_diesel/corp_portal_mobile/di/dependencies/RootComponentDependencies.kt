package ru.kama_diesel.corp_portal_mobile.di.dependencies

import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.data.network.api.CorpPortalApi
import ru.kama_diesel.corp_portal_mobile.common.data.preferences.IPreferences
import ru.kama_diesel.corp_portal_mobile.feature.root.component.api.IRootComponentDependencies

@Inject
class RootComponentDependencies(
    lazyPreferences: Lazy<IPreferences>,
    lazyCorpPortalApi: Lazy<CorpPortalApi>,
) : IRootComponentDependencies {
    override val preferences by lazyPreferences
    override val corpPortalApi by lazyCorpPortalApi
}
