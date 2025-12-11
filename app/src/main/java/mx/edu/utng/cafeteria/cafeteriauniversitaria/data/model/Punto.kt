package mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model

import com.google.firebase.Timestamp

data class Punto(
    val id: String = "",
    val id_usuario: String = "",
    val cantidad: Int = 0,
    val fecha_obtenido: Timestamp = Timestamp.now(),
    val id_promocion: String? = null
)