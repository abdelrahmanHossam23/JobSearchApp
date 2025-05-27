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
import com.example.myjobsearchapplication.ui.common_components.ThemeManager



private val LightColorScheme = lightColorScheme(
    primary = LightAzure,
    secondary = Cyan,
    tertiary = LightBlue,
    background = SnowWhite,
    surface = PaleGray,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onBackground = Charcoal,
    onSurface = Color.Black,
)



private val DarkColorScheme = darkColorScheme(
    primary = DarkAzure,
    secondary = Cyan,
    tertiary = DarkCeruleanBlue,
    background = MidnightBlue,
    surface = SpaceCadetBlue,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Cloud,
    onSurface = Cloud,
)


@Composable
fun JobSearchApplicationTheme(
    darkTheme: Boolean = ThemeManager.isDarkTheme.value,
    content: @Composable () -> Unit
) {

    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}