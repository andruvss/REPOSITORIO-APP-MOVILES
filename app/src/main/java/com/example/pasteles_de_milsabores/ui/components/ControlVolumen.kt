package com.example.pasteles_de_milsabores.ui.components

import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ControlVolumen(
    valor: Float,
    onVolumenChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    Slider(
        value = valor,
        onValueChange = onVolumenChange,
        valueRange = 0f..1f,
        modifier = modifier
    )
}