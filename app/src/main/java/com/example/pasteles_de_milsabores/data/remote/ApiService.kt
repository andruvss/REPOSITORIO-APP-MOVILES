package com.example.pasteles_de_milsabores.data.remote

import com.example.pasteles_de_milsabores.data.model.Post
import retrofit2.http.GET

//Esta interfaz define los endpoints HTTP
interface ApiService {

    //Define una solicitud GET al endpoint /post
    @GET("/posts")
    suspend fun getPosts(): List<Post>
}