package mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model

import com.google.firebase.Timestamp

data class Pago(
    val id: String = "",
    val id_pedido: String = "",
    val metodo_pago: String = "", // efectivo, tarjeta, puntos
    val monto: Double = 0.0,
    val fecha_pago: Timestamp = Timestamp.now()
)