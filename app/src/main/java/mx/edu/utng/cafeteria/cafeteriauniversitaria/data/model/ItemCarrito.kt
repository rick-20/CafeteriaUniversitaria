package mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model

data class ItemCarrito(
    val producto: Producto,
    var cantidad: Int = 1
) {
    val subtotal: Double
        get() = producto.precio * cantidad
}