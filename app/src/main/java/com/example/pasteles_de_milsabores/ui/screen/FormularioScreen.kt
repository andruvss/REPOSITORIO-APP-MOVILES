package com.example.pasteles_de_milsabores.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pasteles_de_milsabores.ui.components.CampoTexto
import com.example.pasteles_de_milsabores.ui.components.ControlVolumen
import com.example.pasteles_de_milsabores.ui.components.SeleccionarGenero
import com.example.pasteles_de_milsabores.viewmodel.FormularioViewModel

@Composable
fun FormularioScreen(
    onEnviar: () -> Unit,
    viewModel: FormularioViewModel = viewModel()
) {
    val estado by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CampoTexto(
            valor = estado.nombre,
            onValueChange = viewModel::onNombreChange,
            etiqueta = "Nombre",
            modifier = Modifier.fillMaxWidth()
        )

        CampoTexto(
            valor = estado.correo,
            onValueChange = viewModel::onCorreoChange,
            etiqueta = "Correo",
            modifier = Modifier.fillMaxWidth()
        )

        SeleccionarGenero(
            valor = estado.genero,
            onGeneroChange = viewModel::onGeneroChange,
            modifier = Modifier.fillMaxWidth()
        )

        ControlVolumen(
            valor = estado.volumen,
            onVolumenChange = viewModel::onVolumenChange,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = onEnviar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar")
        }
    }
}