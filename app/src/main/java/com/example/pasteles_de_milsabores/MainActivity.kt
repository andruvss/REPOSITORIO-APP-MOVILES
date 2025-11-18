package com.example.pasteles_de_milsabores

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pasteles_de_milsabores.navigation.AppNavigation
import com.example.pasteles_de_milsabores.ui.screen.BienvenidaScreen
import com.example.pasteles_de_milsabores.ui.screen.CatalogoScreen
import com.example.pasteles_de_milsabores.ui.screen.LoginScreen
import com.example.pasteles_de_milsabores.ui.screen.RegistroScreen
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
fun PreviewCatalogoScreen() {
    // ViewModel falso para mostrar datos en el preview
    val fakeViewModel = object : CatalogoViewModel() {
        init {
            _productos.value = listOf(
                Producto(1, "Torta de Chocolate", 15000, "Torta rica"),
                Producto(2, "Cheesecake Frutilla", 18000, "Delicioso"),
                Producto(3, "Pie de Limón", 12000, "Clásico favorito")
            )
        }
    }

    CatalogoScreen(viewModel = fakeViewModel)
}

// ---- ViewModel falso para el Preview ----
class FakeUsuarioViewModel : UsuarioViewModel(usuarioDao = FakeUsuarioDao())

class FakeUsuarioDao : com.example.pasteles_de_milsabores.data.UsuarioDao {
    private val lista = mutableListOf<Usuario>()

    override suspend fun insertar(usuario: Usuario) {
        lista.add(usuario)
    }

    override suspend fun obtenerUsuarios(): List<Usuario> {
        return lista
    }
}

// ---- PREVIEW ----
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegistroScreenPreview() {
    RegistroScreen(
        viewModel = FakeUsuarioViewModel(),
        onRegistroExitoso = {}
    )
}

