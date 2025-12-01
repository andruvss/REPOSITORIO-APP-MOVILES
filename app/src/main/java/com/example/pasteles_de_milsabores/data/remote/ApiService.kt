package com.example.pasteles_de_milsabores.data.remote

import com.example.pasteles_de_milsabores.data.model.Post
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

//Esta interfaz define los endpoints HTTP
interface ApiService {

    //Define una solicitud GET al endpoint /post
    @GET("/posts")
    suspend fun getPosts(): List<Post>

    @PUT
    suspend fun updatePost(@Path("id") id: Int, @Body post: Post): Post

    @DELETE("/posts/{id}")
    suspend fun deletePost(@Path("id") id: Int)
}