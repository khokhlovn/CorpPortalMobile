package ru.kama_diesel.corp_portal_mobile.feature.auth.domain.fsm

import me.tatarka.inject.annotations.Inject
import ru.kama_diesel.corp_portal_mobile.feature.auth.domain.di.AuthFlowScope
import ru.kama_diesel.corp_portal_mobile.feature.auth.domain.fsm.actions.AuthFSMAction
import ru.kama_diesel.corp_portal_mobile.feature.auth.domain.fsm.actions.Authenticate
import ru.kama_diesel.corp_portal_mobile.feature.auth.domain.fsm.actions.HandleChangeLoginData
import ru.kontur.mobile.visualfsm.Feature
import ru.kontur.mobile.visualfsm.GenerateTransitionsFactory

@AuthFlowScope
@Inject
@GenerateTransitionsFactory
class AuthFeature(
    initialState: AuthFSMState,
    private val asyncWorker: AuthAsyncWorker
) : Feature<AuthFSMState, AuthFSMAction>(
    initialState = initialState,
    asyncWorker = asyncWorker,
    transitionsFactory = GeneratedAuthFeatureTransitionsFactory()
) {
    fun startAuthenticating() {
        proceed(Authenticate())
    }

    fun handleChangeLoginData(name: String, password: String) {
        proceed(HandleChangeLoginData(name, password))
    }

    fun onDestroy() {
        asyncWorker.unbind()
    }
}