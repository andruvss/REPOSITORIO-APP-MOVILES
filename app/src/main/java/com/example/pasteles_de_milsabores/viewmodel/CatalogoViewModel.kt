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

    // --- LÓGICA DEL CARRITO ---

    // El carrito guarda el producto y la cantidad pedida
    // NOTA: Para simplificar, seguimos guardando solo el Producto en la lista.
    private val _carrito = MutableStateFlow<List<Producto>>(emptyList())
    val carrito = _carrito.asStateFlow()

    // ✨ LAS FUNCIONES ANTERIORES DE 'agregarAlCarrito' Y 'quitarDelCarrito' SE REEMPLAZAN
    //    POR LAS NUEVAS FUNCIONES DE STOCK ABAJO.

    // Función para vaciar el carrito (al comprar)
    fun vaciarCarrito() {
        // NOTA: Si vacías el carrito, ¡deberías devolver el stock!
        // Por ahora, solo vaciamos la lista:
        _carrito.value = emptyList()
    }

    // 5. Calcular el total a pagar
    fun calcularTotal(): Double {
        // Usamos Double en lugar de Int para el cálculo de precios
        return _carrito.value.sumOf { it.precio }
    }

    // --- LÓGICA DE STOCK Y CARRITO (NUEVAS FUNCIONES) ---

    // 1. Botón MÁS (+) en el catálogo: Agrega al Carrito y DISMINUYE Stock
    fun agregarAlCarritoYDisminuirStock(producto: Producto) {
        if (producto.stock > 0) {
            viewModelScope.launch {
                // a) Disminuir Stock en la DB
                val productoActualizado = producto.copy(stock = producto.stock - 1)
                productoDao.actualizar(productoActualizado)

                // b) Agregar a la lista del Carrito (en memoria)
                _carrito.value = _carrito.value + producto // Agregamos la versión original

                // c) Refrescar la lista de productos en el catálogo
                _productos.value = productoDao.obtenerProductos()
            }
        }
    }

    // 2. Botón MENOS (-) en el catálogo: Quita del Carrito y AUMENTA Stock
    fun removerDelCarritoYAumentarStock(producto: Producto) {
        // Solo procedemos si el producto está en el carrito
        val productoAQuitar = _carrito.value.firstOrNull { it.id == producto.id }

        if (productoAQuitar != null) {
            viewModelScope.launch {
                // a) Aumentar Stock en la DB
                val productoActualizado = producto.copy(stock = producto.stock + 1)
                productoDao.actualizar(productoActualizado)

                // b) Quitar la primera ocurrencia de la lista del Carrito
                val listaActual = _carrito.value.toMutableList()
                listaActual.remove(productoAQuitar)
                _carrito.value = listaActual

                // c) Refrescar la lista de productos en el catálogo
                _productos.value = productoDao.obtenerProductos()
            }
        }
    }


    fun agregarProducto(nombre: String, descripcion: String, precio: Double, stock: Int, imagenUrl: String? = null) {
        // ✨ Producto ahora recibe stock e imagenUrl
        val nuevoProducto = Producto(nombre = nombre, descripcion = descripcion, precio = precio, stock = stock, imagenUrl = imagenUrl)

        viewModelScope.launch {
            productoDao.insertar(nuevoProducto)
            _productos.value = productoDao.obtenerProductos()
        }
    }

    // --- FUNCIÓN DE SEEDING MODIFICADA CON STOCK ---
    fun mostrarProductos() {
        viewModelScope.launch {
            var lista = productoDao.obtenerProductos()

            if (lista.isEmpty()) {
                val pastel1 = Producto(
                    nombre = "Torta Chocolate",
                    precio = 15000.0,
                    descripcion = "Torta triple de chocolate amargo, ideal para compartir.",
                    stock = 10, // ✨ STOCK INICIAL
                    imagenUrl = "https://cdn0.recetasgratis.net/es/posts/4/8/8/torta_humeda_de_chocolate_decorada_47884_orig.jpg" // Imagen de ejemplo
                )
                val pastel2 = Producto(
                    nombre = "Pie de Limón",
                    precio = 12000.0,
                    descripcion = "Clásico pie de limón con merengue suizo tostado.",
                    stock = 5, // ✨ STOCK INICIAL
                    imagenUrl = "https://www.recetasnestle.cl/sites/default/files/srh_recipes/49d627e69672b6915c22f2eb2dfd1b93.jpg" // Imagen de ejemplo
                )
                val pastel3 = Producto(
                    nombre = "Cheesecake Berries",
                    precio = 18000.0,
                    descripcion = "Cremoso cheesecake con coulis de frutos rojos de la temporada.",
                    stock = 2, // ✨ STOCK INICIAL (bajo para probar)
                    imagenUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR03IsZyvTkUDZlNE5i5Ux8NVW_-N4iXLqz6A&s" // Imagen de ejemplo
                )

                productoDao.insertar(pastel1)
                productoDao.insertar(pastel2)
                productoDao.insertar(pastel3)

                lista = productoDao.obtenerProductos()
            }

            _productos.value = lista
        }
    }


    fun actualizarProducto(producto: Producto) {
        viewModelScope.launch {
            productoDao.actualizar(producto)
            _productos.value = productoDao.obtenerProductos()
        }
    }

    fun eliminarProducto(producto: Producto) {
        viewModelScope.launch {
            productoDao.eliminar(producto)
            _productos.value = productoDao.obtenerProductos()
        }
    }
}