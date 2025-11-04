package com.example.pasteles_de_milsabores.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pasteles_de_milsabores.ui.screen.BienvenidaScreen
import com.example.pasteles_de_milsabores.ui.screen.LoginScreen
import com.example.pasteles_de_milsabores.ui.screen.RegistroScreen
import com.example.pasteles_de_milsabores.viewmodel.FormularioViewModel
import com.example.pasteles_de_milsabores.viewmodel.LoginViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: FormularioViewModel = viewModel()

    NavHost(navController = navController, startDestination = "bienvenida") {
        composable("bienvenida") {
            BienvenidaScreen(
                onIrAInicioSesion = { navController.navigate("login") },
                onIrARegistro = {navController.navigate("registro") }
            )
        }

        composable("login") {
            val loginViewModel: LoginViewModel = viewModel()
            val state by loginViewModel.uiState.collectAsState()

            // Pasamos el ViewModel al LoginScreen
            LoginScreen(viewModel = loginViewModel)

            // Si el usuario se logeó, navega automáticamente al catálogo
            if (state.estalogeado) {
                navController.navigate("catalogo") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }
        composable("registro") {
            RegistroScreen(
                onRegistroExitoso = {
                    navController.navigate("login") {
                        popUpTo("registro") { inclusive = true } // vuelve al login
                    }
                }
            )
        }
    }
}