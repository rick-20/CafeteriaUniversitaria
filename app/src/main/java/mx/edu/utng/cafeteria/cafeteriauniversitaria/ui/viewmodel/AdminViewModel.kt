package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model.Promocion
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.repository.PromocionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AdminViewModel : ViewModel() {
    private val repository = PromocionRepository()

    private val _promociones = MutableStateFlow<List<Promocion>>(emptyList())
    val promociones: StateFlow<List<Promocion>> = _promociones

    init {
        cargarPromociones()
    }

    private fun cargarPromociones() {
        viewModelScope.launch {
            repository.getPromociones().collect { promociones ->
                _promociones.value = promociones
            }
        }
    }

    fun agregarPromocion(promocion: Promocion) {
        viewModelScope.launch {
            repository.agregarPromocion(promocion)
        }
    }

    fun actualizarPromocion(id: String, promocion: Promocion) {
        viewModelScope.launch {
            repository.actualizarPromocion(id, promocion)
        }
    }

    fun eliminarPromocion(id: String) {
        viewModelScope.launch {
            repository.eliminarPromocion(id)
        }
    }
}