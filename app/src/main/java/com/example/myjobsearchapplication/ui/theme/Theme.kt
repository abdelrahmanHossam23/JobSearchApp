package com.example.myjobsearchapplication.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color


private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF117E7E),
    tertiary = Color(0xFF094F4F),
    onPrimary = Color.White,
    secondary = Color(0xFFEC7121),
    background = Color(0xFFF5F5F5),
    surface = Color.White,
    onSurface = Color(0xFF1C1B1F),
    onSecondary = Color.White,
    error = Color(0xFFDC3545),
    onError = Color.White,
    outline = Color(0xFFCCCCCC),
)

//private val DarkColorScheme = darkColorScheme(
//    primary = Color(0xFF0E5757),
//    tertiary = Color(0xFF093F3F),
//    onPrimary = Color.White,
//    secondary = Color(0xFFAF511D),
//    background = Color(0xFF121212),
//    surface = Color(0xFF1E1E1E),
//    onSurface = Color(0xFFEAEAEA),
//    onSecondary = Color.White,
//    error = Color(0xFFFF4C4C),
//    onError = Color.White,
//    outline = Color(0xFF444444),
//)

//private val DarkColorScheme = darkColorScheme(
//    primary = pri,
//    secondary = Vio,
//    tertiary = Pink600,
//    background = CoolGray800,
//    surface = Cool,
//    onPrimary = Color.White,
//    onSecondary = Color.White,
//    onBackground = Color.White,
//    onSurface = Color.White,
//)

private val DarkColorScheme = darkColorScheme(
    primary = DarkAzure1,
    secondary = Cyan,
    tertiary = Coral, // Keeps vibrancy in dark mode
    background = CoolGray800,
    surface = Cool1,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color(0xFFE0E0E0),
    onSurface = Color(0xFFE0E0E0),
)

@Composable
fun JobSearchApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}