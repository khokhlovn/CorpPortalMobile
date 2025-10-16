package ru.kama_diesel.corp_portal_mobile.feature.auth.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.kama_diesel.corp_portal_mobile.feature.auth.ui.data.LoginViewState
import ru.kama_diesel.corp_portal_mobile.feature.auth.ui.element.LoginScreenContent

@Composable
fun LoginScreen(
    viewState: LoginViewState,
    onChangeLoginData: (String, String) -> Unit,
    startAuthenticating: () -> Unit,
) {
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
        ) {
            LoginScreenContent(
                data = viewState,
                onNameChange = { name ->
                    onChangeLoginData(name, viewState.password)
                },
                onPasswordChange = { password ->
                    onChangeLoginData(viewState.name, password)
                },
                onSignInClick = { startAuthenticating() },
            )
        }
    }
}
