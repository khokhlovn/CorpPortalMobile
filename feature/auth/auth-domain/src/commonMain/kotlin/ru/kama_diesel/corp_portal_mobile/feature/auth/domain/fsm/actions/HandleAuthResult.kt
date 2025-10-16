package ru.kama_diesel.corp_portal_mobile.feature.auth.domain.fsm.actions

import ru.kama_diesel.corp_portal_mobile.feature.auth.domain.data.AuthResult
import ru.kama_diesel.corp_portal_mobile.feature.auth.domain.fsm.AuthFSMState.*
import ru.kontur.mobile.visualfsm.Transition

internal class HandleAuthResult(private val result: AuthResult) : AuthFSMAction() {

    inner class Success : Transition<AsyncWorkState.Authenticating, UserAuthorized>() {
        override fun predicate(state: AsyncWorkState.Authenticating): Boolean {
            return result == AuthResult.SUCCESS
        }

        override fun transform(state: AsyncWorkState.Authenticating): UserAuthorized {
            return UserAuthorized(name = state.name)
        }
    }

    inner class BadCredential : Transition<AsyncWorkState.Authenticating, Login>() {
        override fun predicate(state: AsyncWorkState.Authenticating): Boolean {
            return result == AuthResult.BAD_CREDENTIAL
        }

        override fun transform(state: AsyncWorkState.Authenticating): Login {
            return Login(name = state.name, password = state.password, errorMessage = "Ошибка сети")
        }
    }

    inner class ConnectionFailed : Transition<AsyncWorkState.Authenticating, Login>() {
        override fun predicate(state: AsyncWorkState.Authenticating): Boolean {
            return result == AuthResult.NO_INTERNET
        }

        override fun transform(state: AsyncWorkState.Authenticating): Login {
            return Login(
                name = state.name,
                password = state.password,
                errorMessage = "Проверьте соединение сети Интернет"
            )
        }
    }
}
