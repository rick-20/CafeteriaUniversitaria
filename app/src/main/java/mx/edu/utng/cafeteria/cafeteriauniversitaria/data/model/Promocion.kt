package mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model

import com.google.firebase.Timestamp

data class Promocion(
    val id: String = "",
    val nombre: String = "",
    val descripcion: String = "",
    val puntos_requeridos: Int = 0,
    val fecha_inicio: Timestamp = Timestamp.now(),
    val fecha_fin: Timestamp = Timestamp.now()
)