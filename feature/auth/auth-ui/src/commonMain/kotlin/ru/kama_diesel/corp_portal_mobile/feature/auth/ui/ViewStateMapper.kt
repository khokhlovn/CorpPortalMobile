package ru.kama_diesel.corp_portal_mobile.feature.auth.ui

import ru.kama_diesel.corp_portal_mobile.feature.auth.domain.fsm.AuthFSMState
import ru.kama_diesel.corp_portal_mobile.feature.auth.ui.data.AuthViewState
import ru.kama_diesel.corp_portal_mobile.feature.auth.ui.data.LoginViewState
import ru.kama_diesel.corp_portal_mobile.feature.auth.ui.data.UserAuthorizedViewState

object ViewStateMapper {
    fun map(state: AuthFSMState): AuthViewState {
        return when (state) {
            is AuthFSMState.Login -> LoginViewState(
                name = state.name,
                password = state.password,
                errorMessage = state.errorMessage,
                isAuthenticationInProgress = false,
            )

            is AuthFSMState.AsyncWorkState.Authenticating -> LoginViewState(
                name = state.name,
                password = state.password,
                errorMessage = null,
                isAuthenticationInProgress = true,
            )

            is AuthFSMState.UserAuthorized -> UserAuthorizedViewState(
                name = state.name
            )
        }
    }
}