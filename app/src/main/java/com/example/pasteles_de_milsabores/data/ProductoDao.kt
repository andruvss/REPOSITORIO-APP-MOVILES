package com.example.pasteles_de_milsabores.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.pasteles_de_milsabores.model.Producto

@Dao
interface ProductoDao {
    @Insert
    suspend fun insertar(producto: Producto)

    @Query("SELECT * FROM Producto")
    suspend fun obtenerProductos(): List<Producto>

    // U - Update (Nuevo)
    @Update
    suspend fun actualizar(producto: Producto) // Room detecta el ID y actualiza el resto del objeto

    // D - Delete (Nuevo)
    @Delete
    suspend fun eliminar(producto: Producto) // Elimina el producto basándose en su ID
}