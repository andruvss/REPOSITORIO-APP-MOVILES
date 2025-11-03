package com.example.pasteles_de_milsabores.ui.components

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeleccionarGenero(
    valor: String,
    onGeneroChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val opciones = listOf("Masculino", "Femenino", "Otro")

    ExposedDropdownMenuBox(
        expanded = false,
        onExpandedChange = {}
    ) {
        OutlinedTextField(
            value = valor,
            onValueChange = {},
            readOnly = true,
            label = { Text("Género") },
            modifier = modifier.menuAnchor()
        )
        DropdownMenu(expanded = false, onDismissRequest = { }) {
            opciones.forEach { opcion ->
                DropdownMenuItem(
                    text = { Text(opcion) },
                    onClick = { onGeneroChange(opcion) }
                )
            }
        }
    }
}