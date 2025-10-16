package ru.kama_diesel.corp_portal_mobile.feature.auth.ui.data

data class LoginViewState(
    val name: String,
    val password: String,
    val errorMessage: String?,
    val isAuthenticationInProgress: Boolean,
) : AuthViewState()
