package com.example.pasteles_de_milsabores.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pasteles_de_milsabores.R // Asegúrate de que tu recurso R.drawable.logoprincipal exista
import com.example.pasteles_de_milsabores.ui.theme.BlancoHueso // Importa colores específicos si los usas fuera del Theme
import com.example.pasteles_de_milsabores.ui.theme.MarronSuave

@Composable
fun AdminHomeScreen(onIrAEditarProductos: () -> Unit, onIrAEditarPerfil: () -> Unit) {
    // Surface usará el color de fondo principal de tu tema (Crema)
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 🧁 LOGO DE LA PASTELERÍA (Reutilizando el diseño de BienvenidaScreen)
            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 8.dp,
                color = MaterialTheme.colorScheme.surface, // BlancoHueso
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .height(160.dp)
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logoprincipal),
                    contentDescription = "Logo Pastelería",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }

            // TITULO DE BIENVENIDA DEL ADMIN
            Text(
                text = "¡Bienvenido administrador!",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onBackground, // MarronSuave o TextoGris
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Panel de Control",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.secondary // MarronClaro
            )

            Spacer(modifier = Modifier.height(64.dp))

            // 1. Botón para Edición de Productos
            Button(
                onClick = onIrAEditarProductos,
                modifier = Modifier.fillMaxWidth().height(56.dp)
                // Colores automáticos: Fondo MarronSuave, Texto BlancoHueso
            ) {
                Text("Administrar Productos")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 2. Botón para Editar Perfil
            Button(
                onClick = onIrAEditarPerfil,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    // Usaremos el mismo color primario para que se vea importante
                    containerColor = MarronSuave
                )
            ) {
                Text("Editar Mi Perfil (Admin)", color = BlancoHueso)
            }
        }
    }
}