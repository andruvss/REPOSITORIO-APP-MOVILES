package com.example.pasteles_de_milsabores

import com.example.pasteles_de_milsabores.data.model.Post
import com.example.pasteles_de_milsabores.viewmodel.PostViewModel
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class PostViewModelTest : StringSpec({

   //Creamos una subclase falsa de PostViewModel que sobrescribe el repositorio
    val fakePost = listOf(
        Post(1, 1, "Titulo 1", "Contenido 1"),
        Post(2, 2, "Titulo 2", "Contenido 2")
    )

    val testViewModel = object : PostViewModel() {
        override fun fetchPosts() {
            _postList.value = fakePost
        }
    }

    runTest {
        testViewModel.fetchPosts()
        testViewModel.postList.value shouldContainExactly fakePost
    }
} )