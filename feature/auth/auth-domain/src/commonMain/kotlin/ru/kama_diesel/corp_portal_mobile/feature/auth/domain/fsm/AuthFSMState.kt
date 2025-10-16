package ru.kama_diesel.corp_portal_mobile.feature.auth.domain.fsm

import kotlinx.serialization.Serializable
import ru.kontur.mobile.visualfsm.State

@Serializable
sealed class AuthFSMState : State {

    @Serializable
    data class Login(
        val name: String,
        val password: String,
        val errorMessage: String? = null,
        val snackBarMessage: String? = null,
    ) : AuthFSMState()

    @Serializable
    sealed class AsyncWorkState : AuthFSMState() {
        @Serializable
        data class Authenticating(
            val name: String,
            val password: String
        ) : AsyncWorkState()
    }

    @Serializable
    data class UserAuthorized(val name: String) : AuthFSMState()
}