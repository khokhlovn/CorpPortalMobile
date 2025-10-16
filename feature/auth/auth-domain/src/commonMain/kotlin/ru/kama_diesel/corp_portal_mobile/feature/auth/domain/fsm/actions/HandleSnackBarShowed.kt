package ru.kama_diesel.corp_portal_mobile.feature.auth.domain.fsm.actions

import ru.kama_diesel.corp_portal_mobile.feature.auth.domain.fsm.AuthFSMState
import ru.kontur.mobile.visualfsm.SelfTransition

internal class HandleSnackBarShowed : AuthFSMAction() {

    class SnackBarShowed : SelfTransition<AuthFSMState.Login>() {
        override fun transform(state: AuthFSMState.Login): AuthFSMState.Login {
            return state.copy(snackBarMessage = null)
        }
    }
}