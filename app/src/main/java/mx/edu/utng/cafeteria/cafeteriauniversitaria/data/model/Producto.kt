package mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model

data class Producto(
    val id: String = "",
    val nombre: String = "",
    val descripcion: String = "",
    val precio: Double = 0.0,
    val disponible: Boolean = true,
    val imagenUrl: String = "",
    val categoria: String = ""
)