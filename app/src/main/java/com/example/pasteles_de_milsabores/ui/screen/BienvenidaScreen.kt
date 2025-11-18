package com.example.pasteles_de_milsabores.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pasteles_de_milsabores.R
import com.example.pasteles_de_milsabores.ui.theme.MarronSuave

@Composable
fun BienvenidaScreen(onIrAInicioSesion: () -> Unit, onIrARegistro: () -> Unit) {
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
            // 🧁 LOGO DE LA PASTELERÍA
            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 8.dp,
                color = Color.White,
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
            Text(
                text = "¡Bienvenido a Pasteles de Mil Sabores!",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = MarronSuave,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Descubre nuestros deliciosos sabores y personaliza tu pedido.",
                fontSize = 16.sp,
                color = MarronSuave
            )

            Spacer(modifier = Modifier.height(48.dp))

            Button(onClick = onIrAInicioSesion) {
                Text("Iniciar Sesión")
            }

            Spacer(modifier = Modifier.height(48.dp))

            Button(onClick = onIrARegistro) {
                Text("Registrarse")
            }

        }
    }
}