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

    // --- AGREGAR ESTA LÓGICA DEL CARRITO ---

    // 1. Lista de productos en el carrito (empieza vacía)
    private val _carrito = MutableStateFlow<List<Producto>>(emptyList())
    val carrito = _carrito.asStateFlow()

    // 2. Función para agregar un pastel
    fun agregarAlCarrito(producto: Producto) {
        // Creamos una nueva lista sumando el producto nuevo
        _carrito.value = _carrito.value + producto
    }

    // 3. Función para quitar un pastel (opcional, pero útil)
    fun quitarDelCarrito(producto: Producto) {
        _carrito.value = _carrito.value - producto
    }

    // 4. Función para vaciar el carrito (al comprar)
    fun vaciarCarrito() {
        _carrito.value = emptyList()
    }

    // 5. Calcular el total a pagar
    fun calcularTotal(): Int {
        // Convertimos el precio a String y luego a Int.
        // Si es texto inválido, sumamos 0.
        return _carrito.value.sumOf { it.precio.toString().toIntOrNull() ?: 0 }
    }

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