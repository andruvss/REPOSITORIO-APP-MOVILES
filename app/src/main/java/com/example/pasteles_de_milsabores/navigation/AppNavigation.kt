package com.example.pasteles_de_milsabores.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.pasteles_de_milsabores.ui.screen.BienvenidaScreen
import com.example.pasteles_de_milsabores.ui.screen.LoginScreen
import com.example.pasteles_de_milsabores.ui.screen.RegistroScreen
import com.example.pasteles_de_milsabores.viewmodel.CatalogoViewModel
import com.example.pasteles_de_milsabores.viewmodel.FormularioViewModel
import com.example.pasteles_de_milsabores.viewmodel.LoginViewModel
import com.example.pasteles_de_milsabores.data.UsuarioDatabase
import com.example.pasteles_de_milsabores.data.ProductoDatabase
import com.example.pasteles_de_milsabores.ui.screen.CarritoScreen
import com.example.pasteles_de_milsabores.ui.screen.CatalogoAdminScreen
import com.example.pasteles_de_milsabores.ui.screen.CatalogoClienteScreen
import com.example.pasteles_de_milsabores.ui.screen.ProfileScreen
import com.example.pasteles_de_milsabores.ui.screens.PostScreen
import com.example.pasteles_de_milsabores.viewmodel.PostViewModel
import com.example.pasteles_de_milsabores.viewmodel.UsuarioViewModel
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    val databaseUsuario = remember {
        Room.databaseBuilder(
            context,
            UsuarioDatabase::class.java,
            "usuario.db"
        ).build()
    }

    val databaseProducto = remember {
        Room.databaseBuilder(
            context,
            ProductoDatabase::class.java,
            "producto.db"
        ).build()
    }

    val usuarioViewModel = remember {
        UsuarioViewModel(databaseUsuario.usuarioDao())
    }

    val catalogoViewModel = remember {
        CatalogoViewModel(databaseProducto.productoDao())
    }

    NavHost(navController = navController, startDestination = "bienvenida") {
        composable("bienvenida") {
            BienvenidaScreen(
                onIrAInicioSesion = { navController.navigate("login") },
                onIrARegistro = {navController.navigate("registro") }
            )
        }

        composable("login") {
            val loginViewModel = remember {
                LoginViewModel(databaseUsuario.usuarioDao())
            }
            val state by loginViewModel.uiState.collectAsState()

            LoginScreen(viewModel = loginViewModel)

            LaunchedEffect(state.estalogeado) {
                if (state.estalogeado) {
                    val destino = if (state.esAdmin) "catalogo_admin" else "catalogo_cliente"
                    navController.navigate(destino) {
                        popUpTo("login") { inclusive = true }
                    }
                }
            }
        }
        composable("registro") {
            RegistroScreen(
                viewModel = usuarioViewModel,
                onRegistroExitoso = {
                    navController.navigate("login") {
                        popUpTo("registro") { inclusive = true } // vuelve al login
                    }
                }
            )
        }

        composable("catalogo_cliente") {
            CatalogoClienteScreen(
                viewModel = catalogoViewModel,
                navController = navController // <--- Agrega esto
            )
        }

        // 1. Ruta del Perfil
        composable("perfil") {
            ProfileScreen(
                navController = navController,
                viewModel = usuarioViewModel // ¡IMPORTANTE! Pasamos el mismo VM
            )
        }

        // En AppNavigation.kt, dentro del NavHost

        composable("carrito") {
            CarritoScreen(
                navController = navController,
                viewModel = catalogoViewModel // ¡IMPORTANTE! Usamos el mismo ViewModel
            )
        }

        composable("catalogo_admin") {
            CatalogoAdminScreen(viewModel = catalogoViewModel)
        }


        composable("post") {
            val postViewModel : PostViewModel = viewModel()
            PostScreen(viewModel = postViewModel)
        }
    }
}