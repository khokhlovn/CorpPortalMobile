package ru.kama_diesel.corp_portal_mobile.feature.auth.ui.element

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.stringResource
import ru.kama_diesel.corp_portal_mobile.feature.auth.ui.data.LoginViewState
import ru.kama_diesel.corp_portal_mobile.feature.auth.ui.element.input.NameInputField
import ru.kama_diesel.corp_portal_mobile.feature.auth.ui.element.input.PasswordInputField
import ru.kama_diesel.corp_portal_mobile.resources.Res
import ru.kama_diesel.corp_portal_mobile.resources.login
import ru.kama_diesel.corp_portal_mobile.resources.sign_in

@Composable
fun LoginScreenContent(
    data: LoginViewState,
    onNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignInClick: () -> Unit,
) {
    Column(
        modifier = Modifier.padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        val focusManager = LocalFocusManager.current
        val isError = !data.errorMessage.isNullOrBlank()

        Spacer(modifier = Modifier.height(height = 64.dp))

        LoginText()

        Spacer(modifier = Modifier.height(height = 64.dp))
        NameInputField(
            modifier = Modifier.fillMaxWidth(),
            value = data.name,
            onValueChange = onNameChange
        )
        Spacer(modifier = Modifier.height(height = 16.dp))
        PasswordInputField(
            modifier = Modifier.fillMaxWidth(),
            value = data.password,
            onValueChange = onPasswordChange
        )

        if (isError) {
            Spacer(modifier = Modifier.height(height = 16.dp))
            Text(
                text = data.errorMessage,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(height = 24.dp))
        if (data.isAuthenticationInProgress) {
            CircularProgressIndicator()
        } else {
            SignInButton(
                modifier = Modifier.height(64.dp).fillMaxWidth(),
                onClick = {
                    focusManager.clearFocus()
                    onSignInClick()
                }
            )
        }
    }
}

@Composable
private fun LoginText(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = stringResource(Res.string.login),
        fontSize = 20.sp
    )
}

@Composable
private fun SignInButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        shape = ShapeDefaults.Medium,
        onClick = onClick,
    ) {
        Text(text = stringResource(Res.string.sign_in))
    }
}
