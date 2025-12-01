package com.example.pasteles_de_milsabores.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.copy
import com.example.pasteles_de_milsabores.data.UsuarioDao
import com.example.pasteles_de_milsabores.model.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class LoginViewModel(private val usuarioDao: UsuarioDao) : ViewModel() {

private val _uiState = MutableStateFlow(LoginUiState())
val uiState: StateFlow<LoginUiState> = _uiState

fun onEmailChange(newEmail: String) {
    _uiState.update { it.copy(email = newEmail) }
}

fun onContrasenaChange(newContrasena: String) {
    _uiState.update { it.copy(contrasena = newContrasena) }
}

fun login() {
    val email = _uiState.value.email.trim()
    val password = _uiState.value.contrasena

    viewModelScope.launch {

        if (email.isEmpty() || password.isEmpty()) {
            _uiState.update { it.copy(errorMensaje = "Debe ingresar correo y contraseña") }
        }

        if (password.length < 4) {
            _uiState.update { it.copy(errorMensaje = "La contraseña debe tener al menos 4 caracteres") }
        }


        val usuario = usuarioDao.obtenerUsuarios()
            .find { it.email == email && it.contrasena == password }
        if (usuario != null) {
            _uiState.update { it.copy(estalogeado = true, esAdmin = usuario.rol == "admin", errorMensaje = null) }
        } else {
            _uiState.update { it.copy(errorMensaje = "Correo o contraseña incorrectos") }
        }

    }
}

}

