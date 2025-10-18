package ru.kama_diesel.corp_portal_mobile.common.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import ru.kama_diesel.corp_portal_mobile.common.ui.typography.Typography

@Composable
fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    SystemAppearance(useDarkTheme = useDarkTheme)

    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content,
    )
}
