package com.example.pasteles_de_milsabores.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pasteles_de_milsabores.model.FormularioUiState

@Composable
fun ResumenScreen(estado: FormularioUiState) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Resumen del Formulario:")
        Text("Nombre: ${estado.nombre}")
        Text("Correo: ${estado.correo}")
        Text("Género: ${estado.genero}")
        Text("Volumen: ${(estado.volumen * 100).toInt()}%")
    }
}