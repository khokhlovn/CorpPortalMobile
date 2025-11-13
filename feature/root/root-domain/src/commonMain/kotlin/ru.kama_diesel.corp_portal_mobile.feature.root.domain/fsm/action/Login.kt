package ru.kama_diesel.corp_portal_mobile.feature.root.domain.fsm.action

import ru.kama_diesel.corp_portal_mobile.feature.root.domain.fsm.RootFSMState
import ru.kontur.mobile.visualfsm.Edge
import ru.kontur.mobile.visualfsm.Transition

class Login : RootFSMAction() {

    @Edge("Login")
    inner class OnLogin : Transition<RootFSMState.AuthFlow, RootFSMState.MainFlow>() {
        override fun transform(state: RootFSMState.AuthFlow): RootFSMState.MainFlow {
            return RootFSMState.MainFlow
        }
    }
}
