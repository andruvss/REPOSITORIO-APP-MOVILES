package com.example.pasteles_de_milsabores

import com.example.pasteles_de_milsabores.Repository.PostRepository
import com.example.pasteles_de_milsabores.data.model.Post
import com.example.pasteles_de_milsabores.data.remote.ApiService
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class TestablePostRepository(private val testApi: ApiService) : PostRepository() {
    override suspend fun getPosts(): List<Post> {
        return testApi.getPosts()
    }
}

class PostRepositoryTest : StringSpec( {
    "getPosts() debe retornar una lista de posts simulada" {
        //1. Simulamos el resultado de la API
        val fakePosts = listOf(
            Post(1, 1, "Titulo 1", "Cuerpo 1"),
            Post(2, 2, "Titulo 2", "Cuerpo 2")
        )
        //2. Creamos un mock de ApiService
        val mockApi = mockk<ApiService>()
        coEvery { mockApi.getPosts() } returns fakePosts
        //3. Usamos la clase de test inyectando el mock
        val repo = TestablePostRepository(mockApi)
        //4. Ejecutamos el Test
        runTest {
            val result = repo.getPosts()
            result shouldContainExactly fakePosts
        }
    }
})