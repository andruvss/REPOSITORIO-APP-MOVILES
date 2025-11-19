package com.example.pasteles_de_milsabores

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pasteles_de_milsabores.navigation.AppNavigation
import com.example.pasteles_de_milsabores.ui.screen.BienvenidaScreen
import com.example.pasteles_de_milsabores.ui.screen.CatalogoAdminScreen
import com.example.pasteles_de_milsabores.ui.theme.Pasteles_De_MilSaboresTheme
import com.example.pasteles_de_milsabores.viewmodel.CatalogoViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Pasteles_De_MilSaboresTheme {
                AppNavigation()
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewBienvenidaScreen() {
    BienvenidaScreen(
        onIrAInicioSesion = {},
        onIrARegistro = {}
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCatalogoAdminScreen() {
    CatalogoAdminScreenPreview()
}

@Composable
fun CatalogoAdminScreenPreview() {

    Column(modifier = Modifier.padding(16.dp)) {

        OutlinedTextField(
            value = "Torta de Chocolate",
            onValueChange = {},
            label = { Text("Nombre del producto") }
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = "Una torta muy rica",
            onValueChange = {},
            label = { Text("Descripción") }
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = "15000",
            onValueChange = {},
            label = { Text("Precio") }
        )

        Spacer(Modifier.height(12.dp))

        Button(onClick = {}) {
            Text("Agregar producto")
        }

        Spacer(Modifier.height(24.dp))

        Text("Torta de Chocolate - Torta rica - \$15000")
        Text("Pie de Limón - Clásico - \$12000")
    }
}