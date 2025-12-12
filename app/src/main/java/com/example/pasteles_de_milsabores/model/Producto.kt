package com.example.pasteles_de_milsabores.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Producto")
data class Producto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val imagenUrl: String? = null,
    val stock: Int = 0
)