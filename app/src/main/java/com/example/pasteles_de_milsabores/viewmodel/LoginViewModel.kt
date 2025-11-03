package com.example.pasteles_de_milsabores.viewmodel

import androidx.lifecycle.ViewModel
import androidx.room.util.copy
import com.example.pasteles_de_milsabores.model.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update


class LoginViewModel : ViewModel() {

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
    val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$")

    when {
        email.isEmpty() || password.isEmpty() -> {
            _uiState.update { it.copy(errorMensaje = "Debe ingresar correo y contraseña") }
        }

        !emailRegex.matches(email) -> {
            _uiState.update { it.copy(errorMensaje = "Formato de correo inválido") }
        }

        password.length < 4 -> {
            _uiState.update { it.copy(errorMensaje = "La contraseña debe tener al menos 4 caracteres") }
        }

        email == "admin@gmail.com" && password == "1234" -> {
            _uiState.update { it.copy(estalogeado = true, errorMensaje = null) }
        }

        else -> {
            _uiState.update { it.copy(errorMensaje = "Correo o contraseña incorrectos") }
        }
    }
}

}

