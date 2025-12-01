package com.example.pasteles_de_milsabores.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pasteles_de_milsabores.Repository.PostRepository
import com.example.pasteles_de_milsabores.data.model.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

//ViewModel que mantiene el estado de los datos obtenidos
open class PostViewModel : ViewModel() {

    private val repository = PostRepository()
    //Flujo mutable que contiene la lista de posts

    private val _postList = MutableStateFlow<List<Post>>(emptyList())
    //Flujo público de solo lectura

    val postList: StateFlow<List<Post>> = _postList

    //Se llama automaticamente al iniciar
    init {
        fetchPosts()
    }

    //Funcion que obtiene los datos en segundo plano
    open fun fetchPosts() {
        viewModelScope.launch {
            try {
                _postList.value = repository.getPosts()
            } catch (e: Exception) {
                println("Error al obtener datos: ${e.localizedMessage}")
            }
        }
    }

}