package com.example.pasteles_de_milsabores

import com.example.pasteles_de_milsabores.Repository.PostRepository
import com.example.pasteles_de_milsabores.data.model.Post
import com.example.pasteles_de_milsabores.data.remote.ApiService
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class PostRepositoryTest : StringSpec({

    // 1. Mockeamos el servicio API (la dependencia de red)
    val mockApiService = mockk<ApiService>()

    // 2. Creamos el Repositorio inyectando el mock
    val repository = PostRepository(mockApiService)

    // 3. Datos de prueba
    val fakePostList = listOf(
        Post(userId = 1, id = 1, title = "Test Get", body = "Body Get"),
        Post(userId = 2, id = 2, title = "Test Get 2", body = "Body Get 2")
    )

    // --- Tests de Funcionalidad ---

    "El Repositorio debe llamar a la API y devolver la lista de Posts" {
        runTest {
            // Definimos qué debe devolver el mock cuando se le llama a 'getPosts'
            coEvery { mockApiService.getPosts() } returns fakePostList

            val result = repository.getPosts()

            // Verificamos el resultado
            result shouldContainExactly fakePostList

            // Verificamos que la función de la API fue llamada
            coVerify(exactly = 1) { mockApiService.getPosts() }
        }
    }

    "El Repositorio debe llamar a la API para crear un nuevo Post" {
        runTest {
            val newPost = Post(userId = 99, id = 0, title = "New Post", body = "Content")
            val createdPost = Post(userId = 99, id = 101, title = "New Post", body = "Content")

            // Cuando la API reciba 'newPost', devolverá 'createdPost'
            coEvery { mockApiService.createPost(newPost) } returns createdPost

            val result = repository.createPost(newPost)

            // Verificamos que el resultado es el post con el ID generado
            result shouldBe createdPost

            // Verificamos la llamada
            coVerify(exactly = 1) { mockApiService.createPost(newPost) }
        }
    }

    "El Repositorio debe llamar a la API para actualizar un Post" {
        runTest {
            val originalPost = fakePostList.first()
            val updatedPost = originalPost.copy(title = "Título Editado")

            // Cuando la API reciba la llamada, devuelve el post actualizado
            coEvery { mockApiService.updatePost(updatedPost.id, updatedPost) } returns updatedPost

            val result = repository.updatePost(updatedPost.id, updatedPost)

            // Verificamos que el resultado es el post actualizado
            result shouldBe updatedPost

            // Verificamos la llamada
            coVerify(exactly = 1) { mockApiService.updatePost(updatedPost.id, updatedPost) }
        }
    }

    "El Repositorio debe llamar a la API para eliminar un Post por ID" {
        runTest {
            val postIdToDelete = 50

            // Definimos que la llamada a deletePost() no devolverá nada (Unit)
            coEvery { mockApiService.deletePost(postIdToDelete) } returns Unit

            repository.deletePost(postIdToDelete)

            // Verificamos que la función de la API fue llamada con el ID correcto
            coVerify(exactly = 1) { mockApiService.deletePost(postIdToDelete) }
        }
    }
})