package com.example.pasteles_de_milsabores.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = RosaMedio,         // botones, accents
    onPrimary = BlancoHueso,
    secondary = RosaPastel,      // bordes, highlights
    onSecondary = MarronSuave,
    background = Crema,          // fondo de pantallas
    surface = BlancoHueso,       // cards / surfaces
    onBackground = MarronSuave,
    onSurface = MarronSuave,
)

@Composable
fun Pasteles_De_MilSaboresTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        // opcional: podrías agregar una paleta oscura si quieres
        LightColors
    } else {
        LightColors
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography(), // deja la tipografía por defecto o personaliza
        content = content
    )
}
