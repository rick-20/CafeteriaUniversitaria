package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model.Usuario
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val repository = AuthRepository()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    private val _currentUser = MutableStateFlow<Usuario?>(null)
    val currentUser: StateFlow<Usuario?> = _currentUser

    init {
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        val user = repository.currentUser
        if (user != null) {
            viewModelScope.launch {
                val result = repository.getUserData(user.uid)
                result.onSuccess { usuario ->
                    _currentUser.value = usuario
                    _authState.value = AuthState.Authenticated(usuario)
                }
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = repository.login(email, password)
            result.onSuccess { firebaseUser ->
                val userDataResult = repository.getUserData(firebaseUser.uid)
                userDataResult.onSuccess { usuario ->
                    _currentUser.value = usuario
                    _authState.value = AuthState.Authenticated(usuario)
                }.onFailure { error ->
                    _authState.value = AuthState.Error(error.message ?: "Error desconocido")
                }
            }.onFailure { error ->
                _authState.value = AuthState.Error(error.message ?: "Error al iniciar sesión")
            }
        }
    }

    fun register(email: String, password: String, nombre: String, telefono: String, idUniversidad: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val usuario = Usuario(
                nombre = nombre,
                telefono = telefono,
                id_universidad = idUniversidad,
                rol = "usuario"
            )
            val result = repository.register(email, password, usuario)
            result.onSuccess { firebaseUser ->
                val userDataResult = repository.getUserData(firebaseUser.uid)
                userDataResult.onSuccess { usuarioCompleto ->
                    _currentUser.value = usuarioCompleto
                    _authState.value = AuthState.Authenticated(usuarioCompleto)
                }
            }.onFailure { error ->
                _authState.value = AuthState.Error(error.message ?: "Error al registrarse")
            }
        }
    }

    fun logout() {
        repository.logout()
        _currentUser.value = null
        _authState.value = AuthState.Idle
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Authenticated(val usuario: Usuario) : AuthState()
    data class Error(val message: String) : AuthState()
}