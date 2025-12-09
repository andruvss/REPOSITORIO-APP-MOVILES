package com.example.pasteles_de_milsabores.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.pasteles_de_milsabores.viewmodel.UsuarioViewModel
import com.example.pasteles_de_milsabores.ui.theme.MarronSuave // Importamos MarronSuave para detalles

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: UsuarioViewModel
) {
    val usuario by viewModel.usuarioActual.collectAsState()

    // 🌟 ESTADOS LOCALES PARA TODOS LOS CAMPOS
    var nombre by remember(usuario?.nombre) { mutableStateOf(usuario?.nombre ?: "") }
    var apellido by remember(usuario?.apellido) { mutableStateOf(usuario?.apellido ?: "") }
    var telefono by remember(usuario?.telefono) { mutableStateOf(usuario?.telefono ?: "") }
    var alias by remember(usuario?.alias) { mutableStateOf(usuario?.alias ?: "") }
    var descripcion by remember(usuario?.descripcion) { mutableStateOf(usuario?.descripcion ?: "") }

    // Campos existentes
    var rut by remember(usuario?.rut) { mutableStateOf(usuario?.rut ?: "") }
    var direccion by remember(usuario?.direccion) { mutableStateOf(usuario?.direccion ?: "") }
    var fotoUri by remember { mutableStateOf<Uri?>(usuario?.fotoPerfilUri?.let { Uri.parse(it) }) }

    // Lanzador para abrir la Galería
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) fotoUri = uri
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Perfil") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                // ✨ Aplicamos el color primario del tema (MarronSuave)
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary // BlancoHueso
                )
            )
        },
        // ✨ Aplicamos el color de fondo principal del tema (Crema)
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // --- FOTO DE PERFIL ---
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    // ✨ Usamos MarronSuave (primary) para el borde
                    .border(2.dp, MarronSuave, CircleShape)
                    .clickable { launcher.launch("image/*") }
            ) {
                if (fotoUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(fotoUri),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Icon(
                        Icons.Default.Person,
                        null,
                        modifier = Modifier.size(60.dp),
                        tint = MaterialTheme.colorScheme.secondary // MarronClaro
                    )
                }
            }
            Text(
                "Toca para cambiar foto",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            // --- CAMPOS ---

            // Fila para Nombre y Apellido
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = apellido,
                    onValueChange = { apellido = it },
                    label = { Text("Apellido") },
                    modifier = Modifier.weight(1f)
                )
            }

            // Fila para Teléfono y Alias
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = telefono,
                    onValueChange = { telefono = it },
                    label = { Text("Teléfono") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = alias,
                    onValueChange = { alias = it },
                    label = { Text("Alias") },
                    modifier = Modifier.weight(1f)
                )
            }


            // Campos existentes
            OutlinedTextField(value = rut, onValueChange = { rut = it }, label = { Text("RUT") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = direccion, onValueChange = { direccion = it }, label = { Text("Dirección") }, modifier = Modifier.fillMaxWidth())

            // Descripción (puede ser multilinea)
            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción (Bio)") },
                modifier = Modifier.fillMaxWidth().height(100.dp)
            )


            Spacer(modifier = Modifier.height(16.dp))

            // --- BOTÓN GUARDAR ---
            Button(
                onClick = {
                    usuario?.let { userActual ->
                        val usuarioActualizado = userActual.copy(
                            nombre = nombre,
                            apellido = apellido, // ✨ Nuevo campo
                            telefono = telefono, // ✨ Nuevo campo
                            alias = alias,       // ✨ Nuevo campo
                            descripcion = descripcion, // ✨ Nuevo campo
                            rut = rut,
                            direccion = direccion,
                            fotoPerfilUri = fotoUri?.toString()
                        )
                        viewModel.actualizarPerfil(usuarioActualizado)
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                // ✨ Aplicamos el color primario del tema
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Guardar Cambios", color = MaterialTheme.colorScheme.onPrimary) // BlancoHueso
            }
        }
    }
}