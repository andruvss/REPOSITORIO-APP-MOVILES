package com.example.pasteles_de_milsabores.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pasteles_de_milsabores.data.ProductoDao
import com.example.pasteles_de_milsabores.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CatalogoViewModel(private val productoDao: ProductoDao) : ViewModel() {

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos = _productos.asStateFlow()

    fun agregarProducto(nombre: String, descripcion: String, precio: Double) {
        val nuevoProducto = Producto(nombre = nombre, descripcion = descripcion, precio = precio)

        viewModelScope.launch {
            productoDao.insertar(nuevoProducto)
            _productos.value = productoDao.obtenerProductos()
        }
    }

    fun mostrarProductos() {
        viewModelScope.launch {
            _productos.value = productoDao.obtenerProductos()
        }
    }
}