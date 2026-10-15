package com.floresvalencia.yourpeak.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.floresvalencia.yourpeak.R

/** Única familia tipográfica de todo el proyecto. */
val ArvoFamily = FontFamily(
    Font(R.font.arvo_regular, FontWeight.Normal),
    Font(R.font.arvo_bold, FontWeight.Bold)
)

private val base = Typography()

private fun TextStyle.arvo() = copy(fontFamily = ArvoFamily)

val AppTypography = Typography(
    displayLarge = base.displayLarge.arvo(),
    displayMedium = base.displayMedium.arvo(),
    displaySmall = base.displaySmall.arvo(),
    headlineLarge = base.headlineLarge.arvo(),
    headlineMedium = base.headlineMedium.arvo(),
    headlineSmall = base.headlineSmall.arvo(),
    titleLarge = base.titleLarge.arvo(),
    titleMedium = base.titleMedium.arvo(),
    titleSmall = base.titleSmall.arvo(),
    bodyLarge = base.bodyLarge.arvo(),
    bodyMedium = base.bodyMedium.arvo(),
    bodySmall = base.bodySmall.arvo(),
    labelLarge = base.labelLarge.arvo(),
    labelMedium = base.labelMedium.arvo(),
    labelSmall = base.labelSmall.arvo()
)
