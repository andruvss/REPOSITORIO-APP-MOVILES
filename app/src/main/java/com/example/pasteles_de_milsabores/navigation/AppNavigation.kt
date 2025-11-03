package com.example.pasteles_de_milsabores.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pasteles_de_milsabores.ui.screen.FormularioScreen
import com.example.pasteles_de_milsabores.ui.screen.ResumenScreen
import com.example.pasteles_de_milsabores.viewmodel.FormularioViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: FormularioViewModel = viewModel()

    NavHost(navController = navController, startDestination = "formulario") {
        composable("formulario") {
            FormularioScreen(onEnviar = { navController.navigate("resumen") }, viewModel)
        }
        composable("resumen") {
            val estado = viewModel.uiState.collectAsState().value
            ResumenScreen(estado)
        }
    }
}