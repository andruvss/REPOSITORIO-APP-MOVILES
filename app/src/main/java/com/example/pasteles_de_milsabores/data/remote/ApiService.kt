package com.example.pasteles_de_milsabores.data.remote

import com.example.pasteles_de_milsabores.data.model.Post
import retrofit2.http.GET
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.POST

//Esta interfaz define los endpoints HTTP
interface ApiService {


    @POST("/posts")
    suspend fun createPost(@Body post: Post): Post

    //Define una solicitud GET al endpoint /post
    @GET("/posts")
    suspend fun getPosts(): List<Post>

    // PUT: Para actualizar
    @PUT("/posts/{id}")
    suspend fun updatePost(@Path("id") id: Int, @Body post: Post): Post

    // DELETE: Para borrar
    @DELETE("/posts/{id}")
    suspend fun deletePost(@Path("id") id: Int)
}