package com.example.pasteles_de_milsabores.model

data class FormularioUiState(
    val nombre: String = "",
    val correo: String = "",
    val genero: String = "",
    val volumen: Float = 0.5f,
    val errores: ErroresFormulario = ErroresFormulario()
)