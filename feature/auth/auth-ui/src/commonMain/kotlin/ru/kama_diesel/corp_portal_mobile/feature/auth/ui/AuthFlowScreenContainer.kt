package ru.kama_diesel.corp_portal_mobile.feature.auth.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ru.kama_diesel.corp_portal_mobile.feature.auth.ui.data.LoginViewState
import ru.kama_diesel.corp_portal_mobile.feature.auth.ui.data.UserAuthorizedViewState
import ru.kama_diesel.corp_portal_mobile.feature.auth.ui.screen.LoginScreen

@Composable
fun AuthFlowScreenContainer(viewModel: AuthViewModel) {

    val viewState by viewModel.viewState.collectAsState()

    when (val authViewState = viewState) {
        is LoginViewState -> LoginScreen(
            viewState = authViewState,
            onChangeLoginData = viewModel::handleChangeLoginData,
            startAuthenticating = viewModel::startAuthenticating,
        )

        is UserAuthorizedViewState -> Unit
    }
}
