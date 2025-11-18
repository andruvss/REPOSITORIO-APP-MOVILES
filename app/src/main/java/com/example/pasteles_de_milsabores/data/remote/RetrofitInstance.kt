package com.example.pasteles_de_milsabores.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Singleton que contiene la configuración de Retrofit
object RetrofitInstance {

    //Se instancia el servicio de la API una sola vez
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}