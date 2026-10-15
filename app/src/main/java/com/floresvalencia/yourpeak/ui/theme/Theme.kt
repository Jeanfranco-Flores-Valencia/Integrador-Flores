package com.floresvalencia.yourpeak.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val PeakColorScheme = darkColorScheme(
    primary = PeakRed,
    onPrimary = Color.White,
    secondary = PeakRedDark,
    background = PeakBackground,
    onBackground = TextPrimary,
    surface = PeakCard,
    onSurface = TextPrimary,
    error = PeakRed
)

@Composable
fun YourPeakTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = PeakColorScheme,
        typography = AppTypography,
        content = content
    )
}
