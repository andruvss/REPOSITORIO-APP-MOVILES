package com.example.pasteles_de_milsabores.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pasteles_de_milsabores.Repository.PostRepository
import com.example.pasteles_de_milsabores.data.model.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// CAMBIO CLAVE AQUÍ: Pasamos el repositorio por constructor con valor por defecto.
// Esto nos permite pasar uno FALSO durante las pruebas.
open class PostViewModel(
    private val repository: PostRepository = PostRepository()
) : ViewModel() {

    // Flujo mutable que contiene la lista de posts (Estado privado)
    protected val _postList = MutableStateFlow<List<Post>>(emptyList())

    // Flujo público de solo lectura para la UI
    open val postList: StateFlow<List<Post>> = _postList

    // Se llama automáticamente al iniciar para cargar los datos
    init {
        fetchPosts()
    }

    // --- GET: Obtener datos ---
    open fun fetchPosts() {
        viewModelScope.launch {
            try {
                // Obtenemos los datos de la API y actualizamos la lista
                _postList.value = repository.getPosts()
            } catch (e: Exception) {
                println("Error al obtener datos: ${e.localizedMessage}")
            }
        }
    }

    // --- PUT: Actualizar dato ---
    fun updatePost(post: Post) {
        viewModelScope.launch {
            try {
                // 1. Llamamos a la API para actualizar en la nube
                val postActualizado = repository.updatePost(post.id, post)

                // 2. Actualizamos la lista localmente
                _postList.value = _postList.value.map { existingPost ->
                    if (existingPost.id == post.id) postActualizado else existingPost
                }
                println("Post actualizado con éxito: ${postActualizado.title}")
            } catch (e: Exception) {
                println("Error al actualizar: ${e.localizedMessage}")
            }
        }
    }

    // --- DELETE: Borrar dato ---
    fun deletePost(post: Post) {
        viewModelScope.launch {
            try {
                // 1. Llamamos a la API para borrar
                repository.deletePost(post.id)

                // 2. Actualizamos la lista local filtrando el post eliminado
                _postList.value = _postList.value.filter { it.id != post.id }
                println("Post eliminado con éxito ID: ${post.id}")
            } catch (e: Exception) {
                println("Error al eliminar: ${e.localizedMessage}")
            }
        }
    }

    // --- POST: Crear dato ---
    fun createPost(post: Post) {
        viewModelScope.launch {
            try {
                // 1. Enviamos al servidor
                val nuevoPost = repository.createPost(post)

                // 2. Agregamos el nuevo post a la lista actual
                _postList.value = _postList.value + nuevoPost

                println("Post creado con éxito: ${nuevoPost.title}")
            } catch (e: Exception) {
                println("Error al crear post: ${e.localizedMessage}")
            }
        }
    }
}