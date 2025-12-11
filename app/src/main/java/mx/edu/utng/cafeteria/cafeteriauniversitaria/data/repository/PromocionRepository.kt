package mx.edu.utng.cafeteria.cafeteriauniversitaria.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model.Promocion
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class PromocionRepository {
    private val firestore = FirebaseFirestore.getInstance()

    fun getPromociones(): Flow<List<Promocion>> = callbackFlow {
        val listener = firestore.collection("promociones")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val promociones = snapshot?.documents?.mapNotNull {
                    it.toObject(Promocion::class.java)?.copy(id = it.id)
                } ?: emptyList()
                trySend(promociones)
            }
        awaitClose { listener.remove() }
    }

    suspend fun agregarPromocion(promocion: Promocion): Result<String> {
        return try {
            val docRef = firestore.collection("promociones").add(promocion).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun actualizarPromocion(id: String, promocion: Promocion): Result<Unit> {
        return try {
            firestore.collection("promociones").document(id).set(promocion).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun eliminarPromocion(id: String): Result<Unit> {
        return try {
            firestore.collection("promociones").document(id).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}