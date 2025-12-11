package mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model

data class Usuario(
    val id: String = "",
    val nombre: String = "",
    val correo: String = "",
    val contraseña: String = "",
    val telefono: String = "",
    val puntos_acumulados: Int = 0,
    val id_universidad: String = "",
    val rol: String = "usuario" // "admin" o "usuario"
)