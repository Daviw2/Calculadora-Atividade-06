package com.example.calculadoraatividade06.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val CalculadoraColors = darkColorScheme(
    primary = Color(0xFF6D8DFF),
    secondary = Color(0xFF8D73FF),
    background = Color(0xFF0B1220),
    surface = Color(0xFF111B2E),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun CalculadoraTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = CalculadoraColors,
        typography = Typography(),
        content = content
    )
}
