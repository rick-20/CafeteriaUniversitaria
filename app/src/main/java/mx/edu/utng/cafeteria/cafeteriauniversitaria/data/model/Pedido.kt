package mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model

import com.google.firebase.Timestamp

data class Pedido(
    val id: String = "",
    val id_usuario: String = "",
    val fecha: Timestamp = Timestamp.now(),
    val total: Double = 0.0,
    val estado: String = "pendiente", // pendiente, en_proceso, completado, cancelado
    val detalles: List<DetallePedido> = emptyList()
)