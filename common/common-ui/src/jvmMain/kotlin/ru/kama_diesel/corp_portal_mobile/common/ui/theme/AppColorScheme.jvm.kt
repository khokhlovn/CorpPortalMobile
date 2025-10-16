package ru.kama_diesel.corp_portal_mobile.common.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

@Composable
internal actual fun appColorScheme(useDarkTheme: Boolean): ColorScheme {
    return when {
        useDarkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
}
