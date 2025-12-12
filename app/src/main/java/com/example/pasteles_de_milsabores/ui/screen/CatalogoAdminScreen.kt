package com.example.pasteles_de_milsabores.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.pasteles_de_milsabores.model.Producto
import com.example.pasteles_de_milsabores.viewmodel.CatalogoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogoAdminScreen(viewModel: CatalogoViewModel) {
    // Estados del formulario para agregar/editar un producto
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var stockInput by remember { mutableStateOf("") } // Campo para editar el stock

    // Estado para el producto que se está editando
    var productoAEditar by remember { mutableStateOf<Producto?>(null) }

    val productos by viewModel.productos.collectAsState()

    // Cargar productos al iniciar
    LaunchedEffect(Unit) {
        viewModel.mostrarProductos()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Administración de Productos") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary, // Color pastel principal
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.primaryContainer // Fondo Crema
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // --- FORMULARIO DE AGREGAR / EDITAR PRODUCTO ---
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = if (productoAEditar == null) "Agregar Nuevo Pastel" else "Editar Producto ID: ${productoAEditar!!.id}",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary,
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

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = precio,
                            onValueChange = { precio = it },
                            label = { Text("Precio") },
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = stockInput,
                            onValueChange = { stockInput = it },
                            label = { Text("Stock") },
                            modifier = Modifier.weight(1f),
                            // Configuración de teclado numérico (si la importación funciona)
                            // keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }

                    Spacer(Modifier.height(12.dp))

                    Button(
                        onClick = {
                            if (nombre.isNotBlank() && descripcion.isNotBlank() && precio.isNotBlank() && stockInput.isNotBlank()) {
                                val nuevoPrecio = precio.toDoubleOrNull() ?: 0.0
                                val nuevoStock = stockInput.toIntOrNull() ?: 0

                                if (productoAEditar == null) {
                                    // Lógica de CREACIÓN
                                    viewModel.agregarProducto(
                                        nombre = nombre,
                                        descripcion = descripcion,
                                        precio = nuevoPrecio,
                                        stock = nuevoStock, // Usamos el stock del formulario
                                        imagenUrl = null // Imagen por defecto
                                    )
                                } else {
                                    // Lógica de ACTUALIZACIÓN (UPDATE)
                                    val productoActualizado = productoAEditar!!.copy(
                                        nombre = nombre,
                                        descripcion = descripcion,
                                        precio = nuevoPrecio,
                                        stock = nuevoStock, // Usamos el stock del formulario
                                        imagenUrl = productoAEditar!!.imagenUrl // CONSERVAMOS la imagen
                                    )
                                    viewModel.actualizarProducto(productoActualizado)
                                }

                                // Limpiar formulario y resetear estado
                                nombre = ""
                                descripcion = ""
                                precio = ""
                                stockInput = ""
                                productoAEditar = null
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text(if (productoAEditar == null) "Agregar producto" else "Guardar Cambios", color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            }


            // --- LISTA DE PRODUCTOS CON ACCIONES CRUD ---
            Text(
                text = "Pasteles Existentes",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            HorizontalDivider(color = MaterialTheme.colorScheme.primary)

            // Usamos LazyColumn para manejar listas grandes de manera eficiente
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(productos) { producto ->
                    ProductoAdminItem(
                        producto = producto,
                        onEdit = {
                            // Cargar datos del producto en el formulario
                            nombre = it.nombre
                            descripcion = it.descripcion
                            precio = it.precio.toString()
                            stockInput = it.stock.toString() // Cargar stock para editar
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
}

// --- COMPONENTE SEPARADO PARA EL ITEM DEL ADMINISTRADOR (REDSEÑADO) ---
@Composable
fun ProductoAdminItem(
    producto: Producto,
    onEdit: (Producto) -> Unit,
    onDelete: (Producto) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // 1. IMAGEN DEL PRODUCTO
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp))
                    .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f))
            ) {
                if (!producto.imagenUrl.isNullOrEmpty()) {
                    Image(
                        painter = rememberAsyncImagePainter(producto.imagenUrl),
                        contentDescription = producto.nombre,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Icon(
                        Icons.Default.Cake,
                        contentDescription = "Pastel",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(48.dp).align(Alignment.Center)
                    )
                }
            }

            // 2. Columna de Textos (Nombre, Descripción, Precio, Stock)
            Column(
                modifier = Modifier.weight(1f).padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = producto.descripcion,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "$${producto.precio}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.width(16.dp))
                    // Mostrar STOCK
                    Text(
                        text = "Stock: ${producto.stock}",
                        style = MaterialTheme.typography.labelLarge,
                        color = if (producto.stock < 5) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.secondary
                    )
                }
            }

            // 3. Botones de Acción
            Row(
                modifier = Modifier.padding(end = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Botón de EDICIÓN
                IconButton(onClick = { onEdit(producto) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar producto",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

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
}