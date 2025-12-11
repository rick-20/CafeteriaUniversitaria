package mx.edu.utng.cafeteria.cafeteriauniversitaria.data.repository



import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model.Usuario
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    val currentUser: FirebaseUser? get() = auth.currentUser

    suspend fun login(email: String, password: String): Result<FirebaseUser> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Result.success(result.user!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(email: String, password: String, usuario: Usuario): Result<FirebaseUser> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = result.user!!.uid

            // Guardar usuario en Firestore
            val nuevoUsuario = usuario.copy(id = uid, correo = email)
            firestore.collection("usuarios").document(uid).set(nuevoUsuario).await()

            Result.success(result.user!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUserData(uid: String): Result<Usuario> {
        return try {
            val snapshot = firestore.collection("usuarios").document(uid).get().await()
            val usuario = snapshot.toObject(Usuario::class.java)
            if (usuario != null) {
                Result.success(usuario)
            } else {
                Result.failure(Exception("Usuario no encontrado"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun logout() {
        auth.signOut()
    }
}
