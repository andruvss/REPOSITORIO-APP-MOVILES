package com.example.pasteles_de_milsabores.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pasteles_de_milsabores.model.Usuario

@Database(entities = [Usuario::class], version = 1)
abstract class UsuarioDatabase : RoomDatabase(){
    abstract fun usuarioDao(): UsuarioDao

}