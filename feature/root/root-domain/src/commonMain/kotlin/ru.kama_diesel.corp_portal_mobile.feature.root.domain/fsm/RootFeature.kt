package ru.kama_diesel.corp_portal_mobile.feature.root.domain.fsm

import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.feature.root.domain.di.RootFlowScope
import ru.kama_diesel.corp_portal_mobile.feature.root.domain.fsm.action.Login
import ru.kama_diesel.corp_portal_mobile.feature.root.domain.fsm.action.Logout
import ru.kama_diesel.corp_portal_mobile.feature.root.domain.fsm.action.RootFSMAction
import ru.kontur.mobile.visualfsm.Feature
import ru.kontur.mobile.visualfsm.GenerateTransitionsFactory

@RootFlowScope
@Inject
@GenerateTransitionsFactory
class RootFeature(
    initialState: RootFSMState,
    private val asyncWorker: RootAsyncWorker
) : Feature<RootFSMState, RootFSMAction>(
    initialState = initialState,
    asyncWorker = asyncWorker,
    transitionsFactory = GeneratedRootFeatureTransitionsFactory()
) {
    fun login() {
        proceed(Login())
    }

    fun logout() {
        proceed(Logout())
    }

    fun onDestroy() {
        asyncWorker.unbind()
    }
}
