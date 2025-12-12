package com.example.pasteles_de_milsabores.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.pasteles_de_milsabores.model.Producto
import com.example.pasteles_de_milsabores.viewmodel.CatalogoViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogoClienteScreen(
    viewModel: CatalogoViewModel,
    navController: NavController
) {
    val productos by viewModel.productos.collectAsState()
    val carrito by viewModel.carrito.collectAsState() // 👈 Estado del carrito
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.mostrarProductos()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(24.dp))
                Text(
                    "Pastelería Mil Sabores",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                HorizontalDivider()

                NavigationDrawerItem( icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    label = { Text("Mi Perfil") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate("perfil")
                    } )
                NavigationDrawerItem(
                    icon = { Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null) }, // ✨ CORRECCIÓN: Icono moderno
                    label = { Text("Cerrar Sesión") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate("login") {
                            popUpTo("login") { inclusive = true }
                        }
                    })
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Catálogo") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menú")
                        }
                    },
                    actions = {
                        IconButton(onClick = { navController.navigate("carrito") }) {
                            BadgedBox(
                                badge = {
                                    if (carrito.isNotEmpty()) {
                                        // Muestra la cantidad total de ítems en el badge
                                        Badge { Text(carrito.size.toString()) }
                                    }
                                }
                            ) {
                                Icon(Icons.Default.ShoppingCart, contentDescription = "Ver Carrito")
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            },
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ) { paddingValues ->

            // LISTA DE PRODUCTOS
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(productos) { producto ->
                    // ✨ CAMBIO CRUCIAL 1: Calcula cuántos de este producto están en el carrito
                    val cantidadEnCarrito = carrito.count { it.id == producto.id }

                    ItemProductoCliente(
                        producto = producto,
                        cantidadEnCarrito = cantidadEnCarrito, // 👈 Se pasa el valor correcto
                        onAumentarStock = {
                            // Este es el botón MENOS (-)
                            viewModel.removerDelCarritoYAumentarStock(producto)
                        },
                        onDisminuirStock = {
                            // Este es el botón MÁS (+)
                            viewModel.agregarAlCarritoYDisminuirStock(producto)
                        }
                    )
                }
            }
        }
    }
}

// --- ITEM PRODUCTO CLIENTE REDISEÑADO CON STOCK E IMAGEN ---
@Composable
fun ItemProductoCliente(
    producto: Producto,
    cantidadEnCarrito: Int, // 👈 Se recibe el valor correcto
    onAumentarStock: () -> Unit,
    onDisminuirStock: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {

            // --- ÁREA DE IMAGEN ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                    .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f))
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
                        modifier = Modifier
                            .size(72.dp)
                            .align(Alignment.Center)
                    )
                }
            }

            // --- DETALLES Y BOTONES ---
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                // Columna de Textos
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = producto.nombre,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = producto.descripcion,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "$${producto.precio}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Stock: ${producto.stock}",
                        style = MaterialTheme.typography.labelMedium,
                        color = if (producto.stock > 5) Color.Gray else MaterialTheme.colorScheme.error // Rojo si stock es bajo
                    )
                }

                Spacer(Modifier.width(16.dp))

                // Control de Cantidad (Botones +/-)
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    // Botón MÁS (+) -> Agrega al Carrito (Disminuye Stock)
                    Button(
                        onClick = onDisminuirStock,
                        enabled = producto.stock > 0, // Solo si hay stock
                        modifier = Modifier.size(40.dp),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Agregar al carrito")
                    }

                    Spacer(Modifier.height(8.dp))

                    // ✨ CAMBIO CLAVE: Muestra la cantidad real en el carrito
                    Text(
                        text = cantidadEnCarrito.toString(),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(Modifier.height(8.dp))

                    // Botón MENOS (-) -> Quita del Carrito (Aumenta Stock)
                    Button(
                        onClick = onAumentarStock,
                        enabled = cantidadEnCarrito > 0, // 👈 Habilitado si el producto está en el carrito
                        modifier = Modifier.size(40.dp),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) {
                        Icon(Icons.Default.Remove, contentDescription = "Quitar del carrito")
                    }
                }
            }
        }
    }
}