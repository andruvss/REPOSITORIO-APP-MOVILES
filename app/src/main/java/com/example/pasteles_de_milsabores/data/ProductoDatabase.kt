package com.example.pasteles_de_milsabores.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pasteles_de_milsabores.model.Producto

@Database(entities = [Producto::class], version = 1, exportSchema = false)
abstract class ProductoDatabase : RoomDatabase() {
    abstract fun productoDao(): ProductoDao
}