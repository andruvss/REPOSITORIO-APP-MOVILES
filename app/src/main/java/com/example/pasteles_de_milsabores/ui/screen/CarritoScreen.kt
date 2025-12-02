package com.example.pasteles_de_milsabores.ui.screen


import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Carrito") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFF8BBD0))
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            // LISTA DE PRODUCTOS
            if (carrito.isEmpty()) {
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text("Tu carrito está vacío 😢", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(carrito) { producto ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(producto.nombre, fontWeight = FontWeight.Bold)
                                    Text("$ ${producto.precio}", color = Color(0xFFD81B60))
                                }
                                IconButton(onClick = { viewModel.quitarDelCarrito(producto) }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Borrar", tint = Color.Gray)
                                }
                            }
                        }
                    }
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            // TOTAL Y BOTÓN PAGAR
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Total:", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text("$ $total", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFFD81B60))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (carrito.isNotEmpty()) {
                        viewModel.vaciarCarrito()
                        Toast.makeText(context, "¡Compra realizada con éxito! 🎂", Toast.LENGTH_LONG).show()
                        navController.popBackStack() // Volver al catálogo
                    } else {
                        Toast.makeText(context, "Agrega productos primero", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD81B60))
            ) {
                Text("Confirmar Compra")
            }
        }
    }
}