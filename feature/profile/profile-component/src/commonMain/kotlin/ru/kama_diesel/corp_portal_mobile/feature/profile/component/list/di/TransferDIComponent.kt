package ru.kama_diesel.corp_portal_mobile.feature.profile.component.list.di

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import ru.kama_diesel.corp_portal_mobile.feature.profile.component.di.ProfileFlowDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.profile.domain.di.ProfileScope
import ru.kama_diesel.corp_portal_mobile.feature.profile.domain.di.TransferScope
import ru.kama_diesel.corp_portal_mobile.feature.profile.ui.screen.profile.ProfileViewModel
import ru.kama_diesel.corp_portal_mobile.feature.profile.ui.screen.profile.model.ProfileViewState
import ru.kama_diesel.corp_portal_mobile.feature.profile.ui.screen.transfer.TransferViewModel
import ru.kama_diesel.corp_portal_mobile.feature.profile.ui.screen.transfer.model.TransferViewState

@TransferScope
@Component
abstract class TransferDIComponent(
    @Component val parent: ProfileFlowDIComponent,
    private val initialState: TransferViewState,
) : InstanceKeeper.Instance {

    abstract val viewModel: TransferViewModel

    @Provides
    protected fun getInitialState(): TransferViewState = initialState

    override fun onDestroy() {
        viewModel.onDestroy()
    }
}
