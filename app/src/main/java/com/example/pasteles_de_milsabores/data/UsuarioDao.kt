package com.example.pasteles_de_milsabores.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pasteles_de_milsabores.model.Usuario

@Dao
interface UsuarioDao {
    @Insert
    suspend fun insertar(usuario: Usuario)

    @Query("SELECT * FROM usuarios")
    suspend fun obtenerUsuarios(): List<Usuario>



}