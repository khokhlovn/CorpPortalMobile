package ru.kama_diesel.corp_portal_mobile.feature.root.domain.fsm.action

import ru.kama_diesel.corp_portal_mobile.feature.root.domain.fsm.RootFSMState
import ru.kontur.mobile.visualfsm.Edge
import ru.kontur.mobile.visualfsm.Transition

class Logout : RootFSMAction() {

    @Edge("Logout")
    class OnLogout : Transition<RootFSMState.ArticlesFlow, RootFSMState.AuthFlow>() {
        override fun transform(state: RootFSMState.ArticlesFlow): RootFSMState.AuthFlow {
            return RootFSMState.AuthFlow
        }
    }
}
