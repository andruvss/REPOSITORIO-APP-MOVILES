package com.example.pasteles_de_milsabores

// Importaciones necesarias para pruebas de UI en Compose
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.pasteles_de_milsabores.data.model.Post
import com.example.pasteles_de_milsabores.ui.screens.PostScreen
import com.example.pasteles_de_milsabores.viewmodel.PostViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class PostScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun el_titulo_de_post_debe_aparecer_en_pantalla() {

        val fakePosts = listOf(
            Post(userId = 1, id = 1, title = "Titulo 1", body = "Contenido 1"),
            Post(userId = 2, id = 2, title = "Titulo 2", body = "Contenido 2")
        )

        // Creamos una subclase falsa de PostViewModel para inyectar los datos.
        // Sobrescribimos la variable pública 'postList' para que entregue los datos de prueba.
        val fakeViewModel = object : PostViewModel() {
            // Nota: Aquí se sobrescribe la propiedad para evitar llamar al Repositorio real
            override val postList = MutableStateFlow(fakePosts)

            // Sobrescribir fetchPosts() es opcional, pero previene cualquier error de red si se llama.
            override fun fetchPosts() {
                // No hacemos nada para no contactar a la API
            }
        }

        composeRule.setContent {
            PostScreen(viewModel = fakeViewModel)
        }

        // Verificamos que los textos con formato "Titulo: X" se muestren
        composeRule.onNodeWithText("Titulo: Titulo 1").assertIsDisplayed()

        // Verificamos el cuerpo del segundo post
        composeRule.onNodeWithText("Contenido 2").assertIsDisplayed()
    }
}