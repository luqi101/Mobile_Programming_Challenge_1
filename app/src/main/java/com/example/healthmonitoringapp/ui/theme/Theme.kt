package com.example.healthmonitoringapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = HealthGreenDark,
    secondary = HealthTealDark,
    tertiary = HealthBlueDark,
    background = HealthDarkSurface,
    surface = HealthDarkCard,
    surfaceVariant = ColorTokens.DarkSurfaceVariant,
    onPrimary = ColorTokens.DarkOnPrimary,
    onSecondary = ColorTokens.DarkOnPrimary,
    onTertiary = ColorTokens.DarkOnPrimary,
    onBackground = ColorTokens.DarkOnSurface,
    onSurface = ColorTokens.DarkOnSurface,
    onSurfaceVariant = ColorTokens.DarkOnSurfaceVariant
)

private val LightColorScheme = lightColorScheme(
    primary = HealthGreen,
    secondary = HealthTeal,
    tertiary = HealthBlue,
    background = HealthSurface,
    surface = ColorTokens.LightCard,
    surfaceVariant = HealthMint,
    onPrimary = ColorTokens.LightOnPrimary,
    onSecondary = ColorTokens.LightOnPrimary,
    onTertiary = ColorTokens.LightOnPrimary,
    onBackground = HealthOnSurface,
    onSurface = HealthOnSurface,
    onSurfaceVariant = ColorTokens.LightOnSurfaceVariant
)

@Composable
fun HealthMonitoringAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
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

private object ColorTokens {
    val LightCard = androidx.compose.ui.graphics.Color(0xFFFFFFFF)
    val LightOnPrimary = androidx.compose.ui.graphics.Color(0xFFFFFFFF)
    val LightOnSurfaceVariant = androidx.compose.ui.graphics.Color(0xFF54615A)
    val DarkSurfaceVariant = androidx.compose.ui.graphics.Color(0xFF26352F)
    val DarkOnPrimary = androidx.compose.ui.graphics.Color(0xFF062017)
    val DarkOnSurface = androidx.compose.ui.graphics.Color(0xFFE5F0EB)
    val DarkOnSurfaceVariant = androidx.compose.ui.graphics.Color(0xFFB9C8C1)
}
