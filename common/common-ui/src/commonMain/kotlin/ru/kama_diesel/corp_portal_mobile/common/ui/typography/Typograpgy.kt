package ru.kama_diesel.corp_portal_mobile.common.ui.typography

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import ru.kama_diesel.corp_portal_mobile.resources.*

// Declare the font families
object AppFont {
    val Geologica
        @Composable get() = FontFamily(
            Font(Res.font.Geologica_Regular),
            Font(Res.font.Geologica_Cursive_Regular, style = FontStyle.Italic),
            Font(Res.font.Geologica_Medium, FontWeight.Medium),
            Font(Res.font.Geologica_Cursive_Medium, FontWeight.Medium, style = FontStyle.Italic),
            Font(Res.font.Geologica_Bold, FontWeight.Bold),
            Font(Res.font.Geologica_Cursive_Bold, FontWeight.Bold, style = FontStyle.Italic)
        )
}

private val defaultTypography = Typography()
val Typography
    @Composable get() = Typography(
        displayLarge = defaultTypography.displayLarge.copy(fontFamily = AppFont.Geologica),
        displayMedium = defaultTypography.displayMedium.copy(fontFamily = AppFont.Geologica),
        displaySmall = defaultTypography.displaySmall.copy(fontFamily = AppFont.Geologica),

        headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = AppFont.Geologica),
        headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = AppFont.Geologica),
        headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = AppFont.Geologica),

        titleLarge = defaultTypography.titleLarge.copy(fontFamily = AppFont.Geologica),
        titleMedium = defaultTypography.titleMedium.copy(fontFamily = AppFont.Geologica),
        titleSmall = defaultTypography.titleSmall.copy(fontFamily = AppFont.Geologica),

        bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = AppFont.Geologica),
        bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = AppFont.Geologica),
        bodySmall = defaultTypography.bodySmall.copy(fontFamily = AppFont.Geologica),

        labelLarge = defaultTypography.labelLarge.copy(fontFamily = AppFont.Geologica),
        labelMedium = defaultTypography.labelMedium.copy(fontFamily = AppFont.Geologica),
        labelSmall = defaultTypography.labelSmall.copy(fontFamily = AppFont.Geologica)
    )
