package mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model

data class DetallePedido(
    val id_producto: String = "",
    val nombreProducto: String = "",
    val cantidad: Int = 0,
    val precio_unitario: Double = 0.0,
    val subtotal: Double = 0.0
)