package com.example.pasteles_de_milsabores.Repository

import com.example.pasteles_de_milsabores.data.model.Post
import com.example.pasteles_de_milsabores.data.remote.ApiService
import com.example.pasteles_de_milsabores.data.remote.RetrofitInstance

// Agregamos 'apiService' al constructor para que sea testeable
open class PostRepository(private val apiService: ApiService = RetrofitInstance.api) {

    // GET (Ya lo tienes)
    open suspend fun getPosts(): List<Post> {
        return apiService.getPosts()
    }


    //POST
     suspend fun createPost(post: Post): Post {
        return apiService.createPost(post)
    }


    // PUT (Actualizar)
    suspend fun updatePost(id: Int, post: Post): Post {
        return apiService.updatePost(id, post)
    }

    // DELETE (Borrar)
    suspend fun deletePost(id: Int) {
        apiService.deletePost(id)
    }
}