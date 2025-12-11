package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model.DetallePedido
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model.ItemCarrito
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model.Pedido
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.repository.PedidoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PedidoViewModel : ViewModel() {
    private val repository = PedidoRepository()

    private val _pedidos = MutableStateFlow<List<Pedido>>(emptyList())
    val pedidos: StateFlow<List<Pedido>> = _pedidos

    private val _pedidoCreado = MutableStateFlow<Boolean>(false)
    val pedidoCreado: StateFlow<Boolean> = _pedidoCreado

    fun cargarPedidosUsuario(userId: String) {
        viewModelScope.launch {
            repository.getPedidosUsuario(userId).collect { pedidos ->
                _pedidos.value = pedidos
            }
        }
    }

    fun cargarTodosPedidos() {
        viewModelScope.launch {
            repository.getTodosPedidos().collect { pedidos ->
                _pedidos.value = pedidos
            }
        }
    }

    fun crearPedido(userId: String, items: List<ItemCarrito>) {
        viewModelScope.launch {
            val detalles = items.map { item ->
                DetallePedido(
                    id_producto = item.producto.id,
                    nombreProducto = item.producto.nombre,
                    cantidad = item.cantidad,
                    precio_unitario = item.producto.precio,
                    subtotal = item.subtotal
                )
            }

            val pedido = Pedido(
                id_usuario = userId,
                fecha = Timestamp.now(),
                total = items.sumOf { it.subtotal },
                estado = "pendiente",
                detalles = detalles
            )

            val result = repository.crearPedido(pedido)
            result.onSuccess {
                _pedidoCreado.value = true
            }
        }
    }

    fun actualizarEstado(pedidoId: String, nuevoEstado: String) {
        viewModelScope.launch {
            repository.actualizarEstadoPedido(pedidoId, nuevoEstado)
        }
    }

    fun resetPedidoCreado() {
        _pedidoCreado.value = false
    }
}
