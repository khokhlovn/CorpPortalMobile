package ru.kama_diesel.corp_portal_mobile.feature.top.component.list.di

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import ru.kama_diesel.corp_portal_mobile.feature.top.component.di.TopFlowDIComponent
import ru.kama_diesel.corp_portal_mobile.feature.top.domain.di.TopScope
import ru.kama_diesel.corp_portal_mobile.feature.top.ui.screen.TopViewModel
import ru.kama_diesel.corp_portal_mobile.feature.top.ui.screen.model.TopViewState

@TopScope
@Component
abstract class TopDIComponent(
    @Component val parent: TopFlowDIComponent,
    private val initialState: TopViewState,
) : InstanceKeeper.Instance {

    abstract val viewModel: TopViewModel

    @Provides
    protected fun getInitialState(): TopViewState = initialState

    override fun onDestroy() {
        viewModel.onDestroy()
    }
}
