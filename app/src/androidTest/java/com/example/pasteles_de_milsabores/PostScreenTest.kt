package com.example.pasteles_de_milsabores

// Importaciones necesarias para pruebas de UI en Compose
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pasteles_de_milsabores.data.model.Post
import com.example.pasteles_de_milsabores.ui.screens.PostScreen // Asegúrate que este import sea correcto
import com.example.pasteles_de_milsabores.viewmodel.PostViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class PostScreenTest {

    // Regla para poder controlar la actividad y la UI de Compose
    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun el_titulo_de_post_debe_aparecer_en_pantalla() {

        // 1. Simulamos los datos que el ViewModel entregaría
        val fakePosts = listOf(
            Post(userId = 1, id = 1, title = "Titulo 1", body = "Contenido 1"),
            Post(userId = 2, id = 2, title = "Titulo 2", body = "Contenido 2")
        )

        // 2. Creamos una subclase falsa de PostViewModel
        // CORRECCIÓN CLAVE: 'postList' debe escribirse con minúscula inicial
        // para coincidir con la variable original del ViewModel.
        val fakeViewModel = object : PostViewModel() {
            override val postList = MutableStateFlow(fakePosts)
        }

        // 3. Renderizamos el PostScreen pasando nuestro ViewModel falso
        composeRule.setContent {
            // Nota: Tu PostScreen debe aceptar 'viewModel' como parámetro
            PostScreen(viewModel = fakeViewModel)
        }

        // 4. Validamos que los textos aparecen en la pantalla simulada
        composeRule.onNodeWithText("Titulo: Titulo 1").assertIsDisplayed()
        composeRule.onNodeWithText("Titulo: Titulo 2").assertIsDisplayed()
    }
}