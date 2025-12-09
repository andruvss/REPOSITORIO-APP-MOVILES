package com.example.pasteles_de_milsabores.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Usuario")
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val email: String = "",
    val contrasena: String = "",
    val rol: String ="",


    //para el perfilUsuario
    val rut: String = "",
    val direccion: String = "",
    val fotoPerfilUri: String? = null,

    // ✨ NUEVOS CAMPOS SOLICITADOS ✨
    val apellido: String = "",
    val telefono: String = "",
    val alias: String = "",
    val descripcion: String = ""
)
