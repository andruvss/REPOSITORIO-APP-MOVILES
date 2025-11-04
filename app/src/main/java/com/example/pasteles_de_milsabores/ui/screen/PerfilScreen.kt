package com.example.pasteles_de_milsabores.ui.screen



import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pasteles_de_milsabores.R

@Composable
fun PerfilScreen(
    nombre: String,
    correo: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Imagen del usuario (puedes reemplazar por una real o usar un icono por defecto)
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground), // Reemplázalo por tu imagen
            contentDescription = "Foto de perfil",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Perfil de Usuario",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Nombre: $nombre",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Correo: $correo",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { /* Aquí podrías agregar un botón para editar perfil o cerrar sesión */ }) {
            Text("Cerrar Sesión")
        }
    }
}
