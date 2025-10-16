package ru.kama_diesel.corp_portal_mobile.feature.root.domain.fsm.action

import ru.kama_diesel.corp_portal_mobile.feature.root.domain.fsm.RootFSMState
import ru.kontur.mobile.visualfsm.Edge
import ru.kontur.mobile.visualfsm.Transition

class Login : RootFSMAction() {

    @Edge("Login")
    class OnLogin : Transition<RootFSMState.AuthFlow, RootFSMState.ArticlesFlow>() {
        override fun transform(state: RootFSMState.AuthFlow): RootFSMState.ArticlesFlow {
            return RootFSMState.ArticlesFlow
        }
    }
}
