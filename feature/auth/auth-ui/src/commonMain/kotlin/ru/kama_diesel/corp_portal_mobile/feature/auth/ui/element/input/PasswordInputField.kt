package ru.kama_diesel.corp_portal_mobile.feature.auth.ui.element.input

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation

@Composable
fun PasswordInputField(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        placeholder = { Text(text = placeholder) },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
    )
}
