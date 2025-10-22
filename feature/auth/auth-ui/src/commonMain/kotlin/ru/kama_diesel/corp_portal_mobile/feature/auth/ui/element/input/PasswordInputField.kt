package ru.kama_diesel.corp_portal_mobile.feature.auth.ui.element.input

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import org.jetbrains.compose.resources.stringResource
import ru.kama_diesel.corp_portal_mobile.resources.Res
import ru.kama_diesel.corp_portal_mobile.resources.password

@Composable
fun PasswordInputField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
) {
    var passwordVisibility by remember { mutableStateOf(false) }
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        label = { Text(stringResource(Res.string.password)) },
        colors = OutlinedTextFieldDefaults.colors().copy(
            focusedTextColor = MaterialTheme.colorScheme.primary,
            unfocusedTextColor = MaterialTheme.colorScheme.primary,
        ),
        trailingIcon = {
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                if (passwordVisibility) {
                    Icon(imageVector = Icons.Filled.VisibilityOff, contentDescription = "hide-password")
                } else {
                    Icon(imageVector = Icons.Filled.Visibility, contentDescription = "show-password")
                }
            }
        },
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
        ),
    )
}
