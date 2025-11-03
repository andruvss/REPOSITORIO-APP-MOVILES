package com.example.pasteles_de_milsabores.model

data class LoginUiState(
    val email: String = "",
    val contrasena: String = "",
    val estalogeado: Boolean = false,
    val errorMensaje: String? = null
)
