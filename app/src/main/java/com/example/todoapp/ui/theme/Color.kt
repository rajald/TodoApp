package com.example.todoapp.ui.theme

import androidx.compose.ui.graphics.Color

data class TodoColors(
    val primary: Color,
    val surface: Color,
    val background: Color,
    val secondary: Color,
    val tertiary: Color,
    val onPrimary: Color = Color.White,
    val onSecondary: Color = Color.White,
    val onTertiary: Color = Color.White,
    val onBackground: Color = Color(0xFF1C1B1F),
    val onSurface: Color = Color(0xFF1C1B1F),
    val error: Color = Color.Red,
)
val LightColorPalette = TodoColors(
    primary = Color(0xFF6650a4),
    surface = Color(0xFFFFFBFE),
    background = Color(0xFFFFFBFE),
    secondary = Color(0xFF625b71),
    tertiary = Color(0xFF7D5260),
    error = Color(0XFFFF0000)
)

val DarkColorPalette = TodoColors(
    primary = Color(0xFFD0BCFF),
    surface = Color(0xFF1C1B1F),
    background = Color(0xFF1C1B1F),
    secondary = Color(0xFFCCC2DC),
    tertiary = Color(0xFFEFB8C8),
    error = Color(0XFFFF0000)
)