package com.example.pasteles_de_milsabores.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pasteles_de_milsabores.data.UsuarioDao
import com.example.pasteles_de_milsabores.model.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UsuarioViewModel (private val usuarioDao: UsuarioDao) : ViewModel(){
    private val _usuarios = MutableStateFlow<List<Usuario>>(emptyList())

    val usuarios = _usuarios.asStateFlow()

    /////////////////////
    // Usuario actualmente logueado
    private val _usuarioActual = MutableStateFlow<Usuario?>(null)
    val usuarioActual = _usuarioActual.asStateFlow()

    // Función para "recordar" quién entró al hacer Login
    fun setUsuarioLogueado(email: String) {
        viewModelScope.launch {
            val usuario = usuarioDao.obtenerPorEmail(email)
            _usuarioActual.value = usuario
        }
    }

    // Función para actualizar los datos desde el Perfil
    fun actualizarPerfil(usuario: Usuario) {
        viewModelScope.launch {
            usuarioDao.actualizar(usuario)
            _usuarioActual.value = usuario // Actualizamos la vista en vivo
        }
    }

    ////////////////////

    // --- CAMBIO REALIZADO AQUÍ ---
    // Agregamos 'email: String' a los parámetros
    fun agregarUsuarios(nombre: String, email: String, contrasena: String) {

        // Agregamos 'email = email' al crear el objeto
        val nuevoUsuario = Usuario(
            nombre = nombre,
            email = email,
            contrasena = contrasena,
            rol = "cliente"
        )

        viewModelScope.launch {
            usuarioDao.insertar(nuevoUsuario)
            // Actualizamos la lista local para ver los cambios de inmediato (opcional)
            _usuarios.value = usuarioDao.obtenerUsuarios()
        }
    }

    fun mostrarUsuarios(){
        viewModelScope.launch {
            _usuarios.value = usuarioDao.obtenerUsuarios()
        }
    }

    // Función para crear un administrador si no existe (solo para fines de prueba)
    fun crearAdminSiNoExiste(emailAdmin: String, contrasenaAdmin: String) {
        viewModelScope.launch {
            val adminExistente = usuarioDao.obtenerPorEmail(emailAdmin)
            if (adminExistente == null) {
                val adminUsuario = Usuario(
                    nombre = "Admin Maestro",
                    email = emailAdmin,
                    contrasena = contrasenaAdmin,
                    rol = "admin" // ROL DE ADMINISTRADOR
                )
                usuarioDao.insertar(adminUsuario)
            }
        }
    }
}