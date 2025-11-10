package com.example.pasteles_de_milsabores.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = MarronSuave,              // Color principal (botones, títulos)
    onPrimary = BlancoHueso,            // Texto sobre color principal
    primaryContainer = Crema,           // Fondo principal
    onPrimaryContainer = MarronSuave,   // Texto sobre fondo claro
    secondary = MarronClaro,            // Detalles secundarios
    onSecondary = TextoGris,
    background = BlancoHueso,           // Fondo general
    onBackground = MarronSuave,         // Texto general
    surface = BlancoHueso,              // Fondo de tarjetas/superficies
    onSurface = TextoGris               // Texto sobre superficies
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
