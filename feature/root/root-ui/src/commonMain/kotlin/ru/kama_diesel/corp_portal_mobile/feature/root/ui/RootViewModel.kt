package ru.kama_diesel.corp_portal_mobile.feature.root.ui

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.common.ui.base.BaseViewModel
import ru.kama_diesel.corp_portal_mobile.common.ui.navigation.RouterHolder
import ru.kama_diesel.corp_portal_mobile.feature.root.domain.di.RootFlowScope
import ru.kama_diesel.corp_portal_mobile.feature.root.domain.fsm.RootFSMState
import ru.kama_diesel.corp_portal_mobile.feature.root.domain.fsm.RootFeature
import ru.kama_diesel.corp_portal_mobile.feature.root.ui.api.IRootFlowRouter

@RootFlowScope
@Inject
class RootViewModel(
    private val feature: RootFeature,
    routerHolder: RouterHolder<IRootFlowRouter>
) : BaseViewModel() {

    private val router by routerHolder

    init {
        observeState()
    }

    private fun observeState() {
        feature.observeState().onEach { state ->
            when (state) {
                RootFSMState.AsyncWorkState.Initial -> Unit
                RootFSMState.AuthFlow -> router.toAuth()
                RootFSMState.ArticlesFlow -> router.toArticles()
            }

        }.launchIn(coroutineScope)
    }

    override fun onCleared() {
        feature.onDestroy()
    }
}