package ru.kama_diesel.corp_portal_mobile.feature.profile.component.list.di

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import ru.kama_diesel.corp_portal_mobile.feature.profile.component.di.ProfileFlowDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.profile.domain.di.ProfileScope
import ru.kama_diesel.corp_portal_mobile.feature.profile.ui.screen.ProfileViewModel
import ru.kama_diesel.corp_portal_mobile.feature.profile.ui.screen.model.ProfileViewState

@ProfileScope
@Component
abstract class ProfileDIComponent(
    @Component val parent: ProfileFlowDIComponent,
    private val initialState: ProfileViewState,
) : InstanceKeeper.Instance {

    abstract val viewModel: ProfileViewModel

    @Provides
    protected fun getInitialState(): ProfileViewState = initialState

    override fun onDestroy() {
        viewModel.onDestroy()
    }
}
