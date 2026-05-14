package com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val BlueDarkColorScheme = darkColorScheme(
    primary = Purple80,
    onPrimary = Color(0xFF381E72),
    primaryContainer = Color(0xFF4F378B),
    onPrimaryContainer = Color(0xFFEADDFF),
    secondary = PurpleGrey80,
    background = DarkBackground,
    surface = DarkSurface,
    outline = DarkOutline,
    outlineVariant = DarkOutlineVariant
)
private val BlueLightColorScheme = lightColorScheme(
    primary = Purple40,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFEADDFF),
    onPrimaryContainer = Color(0xFF21005D),
    secondary = PurpleGrey40
)

private val GreenDarkColorScheme = darkColorScheme(
    primary = Color(0xFF81C784),
    onPrimary = Color(0xFF00390A),
    primaryContainer = Color(0xFF23532D),
    onPrimaryContainer = Color(0xFF9DF49E),
    secondary = Color(0xFFAED581),
    background = Color(0xFF1B261B),
    surface = Color(0xFF2E3B2E)
)
private val GreenLightColorScheme = lightColorScheme(
    primary = Color(0xFF388E3C),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFC8E6C9),
    onPrimaryContainer = Color(0xFF002100),
    secondary = Color(0xFF689F38)
)

private val RedDarkColorScheme = darkColorScheme(
    primary = Color(0xFFF2B8B5),
    onPrimary = Color(0xFF601410),
    primaryContainer = Color(0xFF8C1D18),
    onPrimaryContainer = Color(0xFFF9DEDC),
    secondary = Color(0xFFF06292),
    background = Color(0xFF2B1A1A),
    surface = Color(0xFF3B2A2A)
)
private val RedLightColorScheme = lightColorScheme(
    primary = Color(0xFFD32F2F),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFDAD6),
    onPrimaryContainer = Color(0xFF410002),
    secondary = Color(0xFFC2185B)
)

@Composable
fun Mobpro1_Assesment2_GameCollectionTheme(
    themeMode: Int = 0,
    themeColor: String = "Blue",
    content: @Composable () -> Unit
) {
    val darkTheme = when (themeMode) {
        1 -> false
        2 -> true
        else -> isSystemInDarkTheme()
    }

    val colorScheme = when (themeColor) {
        "Green" -> if (darkTheme) GreenDarkColorScheme else GreenLightColorScheme
        "Red" -> if (darkTheme) RedDarkColorScheme else RedLightColorScheme
        else -> if (darkTheme) BlueDarkColorScheme else BlueLightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
