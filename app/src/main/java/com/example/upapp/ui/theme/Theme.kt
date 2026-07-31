package com.example.upapp.ui.theme

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = PrimaryGreen,
    onPrimary = Color.White,
    secondary = DarkGreen,
    onSecondary = Color.White,
    tertiary = RosePink,
    background = Color.White,
    onBackground = DarkGray,
    surface = Color.White,
    onSurface = DarkGray,
    surfaceVariant = LightSage
)

// 🟢 NUEVO: Creamos la paleta para el modo oscuro
private val DarkColorScheme = darkColorScheme(
    primary = PrimaryGreen,
    onPrimary = Color.White,
    secondary = LightGreen,
    onSecondary = DarkGray,
    tertiary = RosePink,
    background = Color(0xFF121212), // Fondo oscuro estándar
    onBackground = Color.White,     // Textos blancos sobre el fondo oscuro
    surface = Color(0xFF1E1E1E),    // Superficies oscuras (como tarjetas)
    onSurface = Color.White,        // Textos blancos sobre superficies
    surfaceVariant = Color(0xFF2D2D2D)
)

@Composable
fun UPAPPTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // 🟢 CORRECCIÓN: Elegimos la paleta dependiendo del valor de darkTheme
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val activity = view.context.findActivity()
            activity?.window?.let { window ->
                window.statusBarColor = PrimaryGreen.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

private tailrec fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}