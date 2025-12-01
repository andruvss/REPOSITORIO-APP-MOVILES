package com.example.pasteles_de_milsabores.Repository

import com.example.pasteles_de_milsabores.data.model.Post
import com.example.pasteles_de_milsabores.data.remote.RetrofitInstance

//Este repositorio se encarga de acceder a los datos usando Retrofit
open class PostRepository {

    open suspend fun getPosts(): List<Post> {
        return RetrofitInstance.api.getPosts()
    }

    open suspend fun updatePost(id: Int, post: Post): Post {
        return RetrofitInstance.api.updatePost(id, post)
    }

    open suspend fun deletePost(id: Int) {
        RetrofitInstance.api.deletePost(id)
    }

}