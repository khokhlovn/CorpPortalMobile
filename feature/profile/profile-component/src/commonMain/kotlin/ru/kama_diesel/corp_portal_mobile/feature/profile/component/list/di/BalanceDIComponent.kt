package ru.kama_diesel.corp_portal_mobile.feature.profile.component.list.di

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import ru.kama_diesel.corp_portal_mobile.feature.profile.component.di.ProfileFlowDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.profile.domain.di.BalanceScope
import ru.kama_diesel.corp_portal_mobile.feature.profile.ui.screen.balance.BalanceViewModel
import ru.kama_diesel.corp_portal_mobile.feature.profile.ui.screen.balance.model.BalanceViewState

@BalanceScope
@Component
abstract class BalanceDIComponent(
    @Component val parent: ProfileFlowDIComponent,
    private val initialState: BalanceViewState,
) : InstanceKeeper.Instance {

    abstract val viewModel: BalanceViewModel

    @Provides
    protected fun getInitialState(): BalanceViewState = initialState

    override fun onDestroy() {
        viewModel.onDestroy()
    }
}
