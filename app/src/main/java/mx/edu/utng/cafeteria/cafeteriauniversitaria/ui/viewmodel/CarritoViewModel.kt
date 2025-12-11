package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel

import androidx.lifecycle.ViewModel
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model.ItemCarrito
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CarritoViewModel : ViewModel() {
    private val _items = MutableStateFlow<List<ItemCarrito>>(emptyList())
    val items: StateFlow<List<ItemCarrito>> = _items

    val total: Double
        get() = _items.value.sumOf { it.subtotal }

    val cantidadItems: Int
        get() = _items.value.sumOf { it.cantidad }

    fun agregarProducto(producto: Producto) {
        val itemExistente = _items.value.find { it.producto.id == producto.id }
        if (itemExistente != null) {
            itemExistente.cantidad++
            _items.value = _items.value.toList()
        } else {
            _items.value = _items.value + ItemCarrito(producto, 1)
        }
    }

    fun eliminarProducto(productoId: String) {
        _items.value = _items.value.filter { it.producto.id != productoId }
    }

    fun actualizarCantidad(productoId: String, cantidad: Int) {
        val item = _items.value.find { it.producto.id == productoId }
        if (item != null && cantidad > 0) {
            item.cantidad = cantidad
            _items.value = _items.value.toList()
        }
    }

    fun limpiarCarrito() {
        _items.value = emptyList()
    }
}
