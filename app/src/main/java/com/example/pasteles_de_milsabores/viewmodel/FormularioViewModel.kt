package com.example.pasteles_de_milsabores.viewmodel

import androidx.lifecycle.ViewModel
import com.example.pasteles_de_milsabores.model.FormularioUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class FormularioViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(FormularioUiState())
    val uiState: StateFlow<FormularioUiState> = _uiState

    fun onNombreChange(nuevo: String) {
        _uiState.update { it.copy(nombre = nuevo) }
    }

    fun onCorreoChange(nuevo: String) {
        _uiState.update { it.copy(correo = nuevo) }
    }

    fun onGeneroChange(nuevo: String) {
        _uiState.update { it.copy(genero = nuevo) }
    }

    fun onVolumenChange(nuevo: Float) {
        _uiState.update { it.copy(volumen = nuevo) }
    }

}