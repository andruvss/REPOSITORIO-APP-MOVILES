package com.example.pasteles_de_milsabores

import com.example.pasteles_de_milsabores.Repository.PostRepository
import com.example.pasteles_de_milsabores.data.model.Post
import com.example.pasteles_de_milsabores.viewmodel.PostViewModel
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class PostViewModelTest : StringSpec() {

    // 1. Despachador de prueba que simula el hilo principal (Dispatchers.Main)
    private val testDispatcher = StandardTestDispatcher()
    private val mockRepository = mockk<PostRepository>()

    // Datos de prueba
    private val fakePosts = listOf(
        Post(userId = 1, id = 1, title = "Titulo 1", body = "Contenido 1"),
        Post(userId = 2, id = 2, title = "Titulo 2", body = "Contenido 2")
    )

    // Bloque init para Kotest
    init {
        // Ejecutado antes de cualquier test: Reemplaza Dispatchers.Main por nuestro testDispatcher
        beforeSpec {
            Dispatchers.setMain(testDispatcher)
        }

        // Ejecutado después de todos los tests: Restaura el Dispatchers.Main original
        afterSpec {
            Dispatchers.resetMain()
        }

        "Debe cargar la lista de Posts del repositorio al inicializarse" {
            runTest(testDispatcher) {
                // Configurar el mock para que devuelva los datos cuando el VM lo llame
                coEvery { mockRepository.getPosts() } returns fakePosts

                // 1. Instanciamos el ViewModel (esto llama a init{} y fetchPosts() que usa una coroutine)
                val testViewModel = PostViewModel(mockRepository)

                // 2. Hacemos que el despachador de prueba ejecute todas las coroutines pendientes
                testDispatcher.scheduler.advanceUntilIdle()

                // 3. Verificamos el resultado
                testViewModel.postList.value shouldContainExactly fakePosts

                // 4. Verificamos que la llamada al repositorio ocurrió
                coVerify(exactly = 1) { mockRepository.getPosts() }
            }
        }

        "Debe actualizar un Post localmente despues de un PUT exitoso" {
            runTest(testDispatcher) {
                // Preparamos el estado inicial y el ViewModel
                coEvery { mockRepository.getPosts() } returns fakePosts
                val testViewModel = PostViewModel(mockRepository)
                testDispatcher.scheduler.advanceUntilIdle() // Ejecuta la carga inicial

                // Post a actualizar
                val updatedTitle = "Titulo Actualizado en Test"
                val postToUpdate = fakePosts[0].copy(title = updatedTitle)

                // Configurar el mock para la llamada de updatePost
                coEvery { mockRepository.updatePost(postToUpdate.id, postToUpdate) } returns postToUpdate

                // Llamada a la funcion
                testViewModel.updatePost(postToUpdate)

                // Avanzamos el dispatcher para que la coroutine de update termine
                testDispatcher.scheduler.advanceUntilIdle()

                // Verificamos que el post actualizado esté en la lista
                testViewModel.postList.value[0].title shouldBe updatedTitle
                coVerify(exactly = 1) { mockRepository.updatePost(postToUpdate.id, postToUpdate) }
            }
        }

        "Debe eliminar un Post localmente despues de un DELETE exitoso" {
            runTest(testDispatcher) {
                // Preparamos el estado inicial y el ViewModel
                coEvery { mockRepository.getPosts() } returns fakePosts
                val testViewModel = PostViewModel(mockRepository)
                testDispatcher.scheduler.advanceUntilIdle() // Ejecuta la carga inicial

                // Post a eliminar
                val postToDelete = fakePosts[0]

                // Configurar el mock para la llamada de deletePost
                coEvery { mockRepository.deletePost(postToDelete.id) } returns Unit

                // Llamada a la funcion
                testViewModel.deletePost(postToDelete)

                // Avanzamos el dispatcher para que la coroutine de delete termine
                testDispatcher.scheduler.advanceUntilIdle()

                // Verificamos que solo quede 1 post en la lista
                testViewModel.postList.value.size shouldBe 1
                testViewModel.postList.value.first().id shouldBe fakePosts[1].id
                coVerify(exactly = 1) { mockRepository.deletePost(postToDelete.id) }
            }
        }

        "Debe agregar un nuevo Post localmente despues de un POST exitoso" {
            runTest(testDispatcher) {
                // Preparamos el estado inicial y el ViewModel
                coEvery { mockRepository.getPosts() } returns fakePosts
                val testViewModel = PostViewModel(mockRepository)
                testDispatcher.scheduler.advanceUntilIdle() // Ejecuta la carga inicial

                // Nuevo Post
                val newPost = Post(userId = 3, id = 3, title = "Nuevo Post", body = "Contenido nuevo")
                val postToCreate = newPost.copy(id = 0) // Simula el post sin ID que se envía

                // Configurar el mock para la llamada de createPost
                coEvery { mockRepository.createPost(postToCreate) } returns newPost

                // Llamada a la funcion
                testViewModel.createPost(postToCreate)

                // Avanzamos el dispatcher para que la coroutine de create termine
                testDispatcher.scheduler.advanceUntilIdle()

                // Verificamos que la lista tiene 3 posts y el último es el nuevo
                testViewModel.postList.value.size shouldBe 3
                testViewModel.postList.value.last() shouldBe newPost
                coVerify(exactly = 1) { mockRepository.createPost(postToCreate) }
            }
        }
    }
}