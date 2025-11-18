package com.example.pasteles_de_milsabores.Repository

import com.example.pasteles_de_milsabores.data.model.Post
import com.example.pasteles_de_milsabores.data.remote.RetrofitInstance

//Este repositorio se encarga de acceder a los datos usando Retrofit
class PostRepository {

    suspend fun getPosts(): List<Post> {
        return RetrofitInstance.api.getPosts()
    }

}