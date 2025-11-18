package com.example.pasteles_de_milsabores.data.model

//Esta clase representa un post obtenido desde la API
data class Post(

    val userId: Int, //ID del usuario que creo el post
    val id: Int,    //ID del post
    val title: String,//Titulo del post
    val body: String //Contenido del post

)