package com.example.pasteles_de_milsabores

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.lifecycle.viewmodel.compose.viewModel
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
        //simulamos los datos que el viewmoodel entregaria

        val fakePosts = listOf(
            Post(userId = 1,  id = 1, title = "Titulo 1", body = "Contenido 1"),
            Post(userId = 2,  id = 2, title = "Titulo 2", body = "Contenido 2")
        )

        //subclase falsa de PostViewModel con stateFlow simulado
        val fakeViewModel = object : PostViewModel() {
             override val PostList = MutableStateFlow(fakePosts)
        }
        //Renderizamos el Postscreen con el viewModel falso
        composeRule.setContent {
            PostScreen(viewModel = fakeViewModel)
        }
        //validamos que los titulos se muestren correctamente en la UI
        composeRule.onNodeWithText("Titulo: Titulo 1").assertIsDisplayed()
        composeRule.onNodeWithText("Titulo: Titulo 2").assertIsDisplayed()

    }
}