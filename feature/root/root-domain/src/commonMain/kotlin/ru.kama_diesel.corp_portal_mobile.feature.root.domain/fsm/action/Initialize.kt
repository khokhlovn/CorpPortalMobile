package ru.kama_diesel.corp_portal_mobile.feature.root.domain.fsm.action

import ru.kama_diesel.corp_portal_mobile.feature.root.domain.fsm.RootFSMState
import ru.kontur.mobile.visualfsm.Transition

data class Initialize(val isUserAuthorized: Boolean) : RootFSMAction() {

    inner class UserAuthorized : Transition<RootFSMState.AsyncWorkState.Initial, RootFSMState.ArticlesFlow>() {
        override fun predicate(state: RootFSMState.AsyncWorkState.Initial): Boolean {
            return isUserAuthorized
        }

        override fun transform(state: RootFSMState.AsyncWorkState.Initial): RootFSMState.ArticlesFlow {
            return RootFSMState.ArticlesFlow
        }
    }

    inner class UserNotAuthorized : Transition<RootFSMState.AsyncWorkState.Initial, RootFSMState.AuthFlow>() {
        override fun predicate(state: RootFSMState.AsyncWorkState.Initial): Boolean {
            return !isUserAuthorized
        }

        override fun transform(state: RootFSMState.AsyncWorkState.Initial): RootFSMState.AuthFlow {
            return RootFSMState.AuthFlow
        }
    }
}