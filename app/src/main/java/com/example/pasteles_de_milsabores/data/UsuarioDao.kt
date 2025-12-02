package com.example.pasteles_de_milsabores.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.pasteles_de_milsabores.model.Usuario

@Dao
interface UsuarioDao {
    @Insert
    suspend fun insertar(usuario: Usuario)

    @Query("SELECT * FROM Usuario")
    suspend fun obtenerUsuarios(): List<Usuario>

    @Update
    suspend fun actualizar(usuario: Usuario)

    @Query("SELECT * FROM Usuario WHERE email = :email LIMIT 1")
    suspend fun obtenerPorEmail(email: String): Usuario?

}