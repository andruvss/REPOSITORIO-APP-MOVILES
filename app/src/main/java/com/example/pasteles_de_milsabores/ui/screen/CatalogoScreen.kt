package com.example.pasteles_de_milsabores.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pasteles_de_milsabores.viewmodel.CatalogoViewModel

@Composable
fun CatalogoScreen(viewModel: CatalogoViewModel) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }

    val productos by viewModel.productos.collectAsState()

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre del producto") }
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") }
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = precio,
            onValueChange = { precio = it },
            label = { Text("Precio") }
        )

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = {
                if (nombre.isNotBlank() && descripcion.isNotBlank() && precio.isNotBlank()) {
                    viewModel.agregarProducto(nombre, descripcion, precio.toDouble())
                    nombre = ""
                    descripcion = ""
                    precio = ""
                }
        }) {
            Text("Agregar producto")
        }

        Spacer(Modifier.height(24.dp))

        productos.forEach {
            Text("${it.nombre} - ${it.descripcion} - $${it.precio}")
        }
    }
}