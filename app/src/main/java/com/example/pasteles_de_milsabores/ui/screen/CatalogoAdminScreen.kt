package com.example.pasteles_de_milsabores.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pasteles_de_milsabores.model.Producto
import com.example.pasteles_de_milsabores.viewmodel.CatalogoViewModel

@Composable
fun CatalogoAdminScreen(viewModel: CatalogoViewModel) {
    // Estados del formulario para agregar un producto
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }

    // Estado para el producto que se está editando
    var productoAEditar by remember { mutableStateOf<Producto?>(null) }

    val productos by viewModel.productos.collectAsState()

    // Cargar productos al iniciar (como lo haces en la pantalla cliente)
    LaunchedEffect(Unit) {
        viewModel.mostrarProductos()
    }

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // --- FORMULARIO DE AGREGAR / EDITAR PRODUCTO ---
        Text(
            text = if (productoAEditar == null) "Agregar Nuevo Producto" else "Editar Producto ID: ${productoAEditar!!.id}",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre del producto") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = precio,
            onValueChange = { precio = it },
            label = { Text("Precio") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = {
                if (nombre.isNotBlank() && descripcion.isNotBlank() && precio.isNotBlank()) {
                    val nuevoPrecio = precio.toDoubleOrNull() ?: 0.0

                    if (productoAEditar == null) {
                        // Lógica de CREACIÓN
                        viewModel.agregarProducto(nombre, descripcion, nuevoPrecio)
                    } else {
                        // Lógica de ACTUALIZACIÓN (UPDATE)
                        val productoActualizado = productoAEditar!!.copy(
                            nombre = nombre,
                            descripcion = descripcion,
                            precio = nuevoPrecio
                        )
                        viewModel.actualizarProducto(productoActualizado)
                    }

                    // Limpiar formulario y resetear estado
                    nombre = ""
                    descripcion = ""
                    precio = ""
                    productoAEditar = null
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (productoAEditar == null) "Agregar producto" else "Guardar Cambios")
        }

        Spacer(Modifier.height(24.dp))

        // --- LISTA DE PRODUCTOS CON ACCIONES CRUD ---
        Text(
            text = "Lista de Productos",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        HorizontalDivider()

        // Usamos LazyColumn para manejar listas grandes de manera eficiente
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(productos) { producto ->
                ProductoAdminItem(
                    producto = producto,
                    onEdit = {
                        // Cargar datos del producto en el formulario
                        nombre = it.nombre
                        descripcion = it.descripcion
                        precio = it.precio.toString()
                        productoAEditar = it
                    },
                    onDelete = {
                        viewModel.eliminarProducto(it)
                    }
                )
            }
        }
    }
}

// --- COMPONENTE SEPARADO PARA EL ITEM DEL ADMINISTRADOR ---
@Composable
fun ProductoAdminItem(
    producto: Producto,
    onEdit: (Producto) -> Unit,
    onDelete: (Producto) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Columna de Textos (Nombre, Descripción, Precio)
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = producto.descripcion,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "$${producto.precio}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // Botón de EDICIÓN
            IconButton(onClick = { onEdit(producto) }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar producto",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(Modifier.width(8.dp))

            // Botón de ELIMINACIÓN
            IconButton(onClick = { onDelete(producto) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar producto",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}