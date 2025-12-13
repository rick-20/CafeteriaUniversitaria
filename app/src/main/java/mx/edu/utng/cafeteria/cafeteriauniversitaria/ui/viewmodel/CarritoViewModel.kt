package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model.ItemCarrito
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model.Producto

class CarritoViewModel : ViewModel() {

    private val _items = MutableStateFlow<List<ItemCarrito>>(emptyList())
    val items: StateFlow<List<ItemCarrito>> = _items

    private val _cantidadItems = MutableStateFlow(0)
    val cantidadItems: StateFlow<Int> = _cantidadItems

    val total: Double
        get() = _items.value.sumOf { it.subtotal }

    fun agregarProducto(producto: Producto) {
        val itemExistente = _items.value.find { it.producto.id == producto.id }

        if (itemExistente != null) {
            itemExistente.cantidad++
            _items.value = _items.value.toList()
        } else {
            _items.value = _items.value + ItemCarrito(producto, 1)
        }

        _cantidadItems.value = _items.value.sumOf { it.cantidad }
    }

    fun eliminarProducto(productoId: String) {
        _items.value = _items.value.filter { it.producto.id != productoId }
        _cantidadItems.value = _items.value.sumOf { it.cantidad }
    }

    fun actualizarCantidad(productoId: String, cantidad: Int) {
        val item = _items.value.find { it.producto.id == productoId }
        if (item != null && cantidad > 0) {
            item.cantidad = cantidad
            _items.value = _items.value.toList()
        }
        _cantidadItems.value = _items.value.sumOf { it.cantidad }
    }

    fun limpiarCarrito() {
        _items.value = emptyList()
        _cantidadItems.value = 0
    }
}
