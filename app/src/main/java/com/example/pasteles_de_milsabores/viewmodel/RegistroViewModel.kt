package com.example.pasteles_de_milsabores.viewmodel

import androidx.lifecycle.ViewModel
import com.example.pasteles_de_milsabores.model.RegistroUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class RegistroViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegistroUiState())
    val uiState: StateFlow<RegistroUiState> = _uiState

    fun onNombreChange(nuevo: String) {
        _uiState.update { it.copy(nombre = nuevo) }
    }

    fun onCorreoChange(nuevo: String) {
        _uiState.update { it.copy(correo = nuevo) }
    }

    fun onContrasenaChange(nuevo: String) {
        _uiState.update { it.copy(contrasena = nuevo) }
    }

    fun onConfirmarContrasenaChange(nuevo: String) {
        _uiState.update { it.copy(confirmarContrasena = nuevo) }
    }

    fun registrarUsuario() {
        val state = _uiState.value

        when {
            state.nombre.isEmpty() || state.correo.isEmpty() || state.contrasena.isEmpty() -> {
                _uiState.update { it.copy(errorMensaje = "Por favor complete todos los campos.") }
            }
            state.contrasena != state.confirmarContrasena -> {
                _uiState.update { it.copy(errorMensaje = "Las contraseñas no coinciden.") }
            }
            else -> {
                _uiState.update {
                    it.copy(
                        registroExitoso = true,
                        errorMensaje = null
                    )
                }
            }
        }
    }
}

