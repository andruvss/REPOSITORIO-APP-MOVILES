package com.example.pasteles_de_milsabores.ui.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.pasteles_de_milsabores.model.Producto
import com.example.pasteles_de_milsabores.viewmodel.CatalogoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(
    navController: NavController,
    viewModel: CatalogoViewModel
) {
    val carrito by viewModel.carrito.collectAsState()
    val total = viewModel.calcularTotal()
    val context = LocalContext.current

    // Agrupamos productos por ID para mostrar la cantidad (Ej: Torta (x2))
    val productosAgrupados = carrito.groupBy { it.id }.mapValues { it.value.size }

    // Obtenemos una lista de productos únicos para mostrar en la LazyColumn
    val productosUnicos = carrito.distinctBy { it.id }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Carrito") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary, // Color Pastel Principal
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.primaryContainer // Fondo Crema
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxSize()
        ) {
            // LISTA DE PRODUCTOS
            if (carrito.isEmpty()) {
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(
                        "Tu carrito está vacío 😢\n¡Añade un pastel para empezar!",
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(productosUnicos, key = { it.id }) { producto ->
                        val cantidad = productosAgrupados[producto.id] ?: 0
                        ItemCarrito(
                            producto = producto,
                            cantidad = cantidad,
                            // La función QUITAR debe eliminar UNA unidad y aumentar el stock
                            onQuitarUnidad = { viewModel.removerDelCarritoYAumentarStock(producto) }
                        )
                    }
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp), color = MaterialTheme.colorScheme.secondary)

            // TOTAL Y BOTÓN PAGAR
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Total a Pagar:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    "$ ${String.format("%.2f", total)}", // Formato de precio con dos decimales
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary // Color principal pastel
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (carrito.isNotEmpty()) {
                        // NOTA: Si vacías el carrito aquí, el stock permanece DISMINUIDO.
                        // Esto es el comportamiento esperado de una "Compra".
                        viewModel.vaciarCarrito()
                        Toast.makeText(context, "¡Compra realizada con éxito! 🎂", Toast.LENGTH_LONG).show()
                        navController.popBackStack() // Volver al catálogo
                    } else {
                        Toast.makeText(context, "Agrega productos primero", Toast.LENGTH_SHORT).show()
                    }
                },
                enabled = carrito.isNotEmpty(),
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Confirmar Compra", fontSize = 18.sp, color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}

// --- COMPONENTE ITEM CARRITO REDISEÑADO ---
@Composable
fun ItemCarrito(
    producto: Producto,
    cantidad: Int,
    onQuitarUnidad: () -> Unit // Función para quitar UNA unidad
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), // Blanco Hueso
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // 1. IMAGEN (Miniatura)
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer)
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
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(32.dp).align(Alignment.Center)
                    )
                }
            }

            Spacer(Modifier.width(12.dp))

            // 2. DETALLES DEL PRODUCTO (Nombre, Cantidad, Precio)
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${producto.nombre} (x$cantidad)", // Mostrar cantidad
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "$ ${producto.precio} c/u", // Precio por unidad
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Subtotal: $ ${String.format("%.2f", producto.precio * cantidad)}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            // 3. Botón para Quitar UNA unidad
            IconButton(onClick = onQuitarUnidad) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Quitar una unidad",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}