package com.example.pasteles_de_milsabores.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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

    // 1. Observamos el carrito para el numerito rojo
    val carrito by viewModel.carrito.collectAsState()

    // Estados para el menú lateral
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Cargar productos al iniciar
    LaunchedEffect(Unit) {
        viewModel.mostrarProductos()
    }

    // --- ESTRUCTURA DEL MENÚ LATERAL ---
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(24.dp))
                Text(
                    "Pastelería Mil Sabores",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFFD81B60)
                )
                HorizontalDivider()

                // OPCIÓN 1: IR AL PERFIL
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    label = { Text("Mi Perfil") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate("perfil")
                    }
                )

                // OPCIÓN 2: CERRAR SESIÓN
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.ExitToApp, contentDescription = null) },
                    label = { Text("Cerrar Sesión") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate("login") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                )
            }
        }
    ) {
        // --- CONTENIDO PRINCIPAL ---
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Catálogo") },
                    navigationIcon = {
                        // BOTÓN HAMBURGUESA ☰
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menú")
                        }
                    },
                    // 2. ÍCONO DEL CARRITO A LA DERECHA CON CONTADOR
                    actions = {
                        IconButton(onClick = { navController.navigate("carrito") }) {
                            BadgedBox(
                                badge = {
                                    if (carrito.isNotEmpty()) {
                                        Badge { Text(carrito.size.toString()) }
                                    }
                                }
                            ) {
                                Icon(Icons.Default.ShoppingCart, contentDescription = "Ver Carrito")
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFFF8BBD0) // Rosado Pastel
                    )
                )
            }
        ) { paddingValues ->

            // LISTA DE PRODUCTOS
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(productos) { producto ->
                    // 3. AQUÍ USAMOS LA FUNCIÓN SEPARADA QUE TE AGREGUÉ ABAJO
                    ItemProductoCliente(
                        producto = producto,
                        onAgregar = {
                            viewModel.agregarAlCarrito(producto)
                        }
                    )
                }
            }
        }
    }
}

// --- ESTA ES LA FUNCIÓN QUE TE FALTABA Y QUE HE AGREGADO ---
@Composable
fun ItemProductoCliente(
    producto: Producto,
    onAgregar: () -> Unit // Recibe la acción de agregar
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
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
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFFD81B60)
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = producto.descripcion,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "$${producto.precio}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            // Botón para Agregar al Carrito
            IconButton(onClick = { onAgregar() }) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Agregar al carrito",
                    tint = Color(0xFFD81B60)
                )
            }
        }
    }
}