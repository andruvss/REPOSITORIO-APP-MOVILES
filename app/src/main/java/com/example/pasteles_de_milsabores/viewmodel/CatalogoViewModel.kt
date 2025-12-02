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

    // --- LA CLAVE PARA MOSTRAR PRODUCTOS: FUNCIÓN DE SEEDING ---
    fun mostrarProductos() {
        viewModelScope.launch {
            // 1. Intentamos traer los productos
            var lista = productoDao.obtenerProductos()

            // 2. SI LA LISTA ESTÁ VACÍA -> CREAMOS DATOS FALSOS (SEEDING)
            if (lista.isEmpty()) {
                val pastel1 = Producto(nombre = "Torta Chocolate", precio = 15000.0, descripcion = "Torta triple de chocolate amargo, ideal para compartir.", imagenUrl = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.directoalpaladar.com%2Fpostres%2Ftarta-facil-chocolate-receta-horno-lista-30-minutos&psig=AOvVaw3OwCff7himHGYHhLi38vaE&ust=1764762772442000&source=images&cd=vfe&opi=89978449&ved=0CBUQjRxqFwoTCKDa4OPrnpEDFQAAAAAdAAAAABAL")
                val pastel2 = Producto(nombre = "Pie de Limón", precio = 12000.0, descripcion = "Clásico pie de limón con merengue suizo tostado.", imagenUrl = "https://www.recetasnestle.cl/sites/default/files/srh_recipes/49d627e69672b6915c22f2eb2dfd1b93.jpg")
                val pastel3 = Producto(nombre = "Cheesecake Berries", precio = 18000.0, descripcion = "Cremoso cheesecake con coulis de frutos rojos de la temporada.", imagenUrl = "https://www.wengerhaus.cl/wp-content/uploads/2023/10/19WENGERHAUS-CUADRADAS.jpg")

                productoDao.insertar(pastel1)
                productoDao.insertar(pastel2)
                productoDao.insertar(pastel3)

                // Volvemos a cargar para que aparezcan ahora sí
                lista = productoDao.obtenerProductos()
            }

            // 3. Actualizamos la pantalla
            _productos.value = lista
        }
    }


    fun actualizarProducto(producto: Producto) {
        viewModelScope.launch {
            productoDao.actualizar(producto)
            // Después de actualizar, recargamos la lista para la UI
            _productos.value = productoDao.obtenerProductos()
        }
    }

    fun eliminarProducto(producto: Producto) {
        viewModelScope.launch {
            productoDao.eliminar(producto)
            // Después de eliminar, recargamos la lista para la UI
            _productos.value = productoDao.obtenerProductos()
        }
    }

}