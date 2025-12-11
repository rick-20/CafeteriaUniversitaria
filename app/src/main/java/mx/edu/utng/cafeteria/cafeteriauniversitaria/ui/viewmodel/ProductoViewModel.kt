package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model.Producto
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.repository.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.net.Uri

class ProductoViewModel : ViewModel() {
    private val repository = ProductoRepository()

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        cargarProductos()
    }

    private fun cargarProductos() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getProductos().collect { productos ->
                _productos.value = productos
                _isLoading.value = false
            }
        }
    }

    fun agregarProducto(producto: Producto, imagenUri: Uri?) {
        viewModelScope.launch {
            repository.agregarProducto(producto, imagenUri)
        }
    }

    fun actualizarProducto(id: String, producto: Producto) {
        viewModelScope.launch {
            repository.actualizarProducto(id, producto)
        }
    }

    fun eliminarProducto(id: String) {
        viewModelScope.launch {
            repository.eliminarProducto(id)
        }
    }
}
