package mx.edu.utng.cafeteria.cafeteriauniversitaria.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model.Pedido
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class PedidoRepository {
    private val firestore = FirebaseFirestore.getInstance()

    fun getPedidosUsuario(userId: String): Flow<List<Pedido>> = callbackFlow {
        val listener = firestore.collection("pedidos")
            .whereEqualTo("id_usuario", userId)
            .orderBy("fecha", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val pedidos = snapshot?.documents?.mapNotNull {
                    it.toObject(Pedido::class.java)?.copy(id = it.id)
                } ?: emptyList()
                trySend(pedidos)
            }
        awaitClose { listener.remove() }
    }

    fun getTodosPedidos(): Flow<List<Pedido>> = callbackFlow {
        val listener = firestore.collection("pedidos")
            .orderBy("fecha", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val pedidos = snapshot?.documents?.mapNotNull {
                    it.toObject(Pedido::class.java)?.copy(id = it.id)
                } ?: emptyList()
                trySend(pedidos)
            }
        awaitClose { listener.remove() }
    }

    suspend fun crearPedido(pedido: Pedido): Result<String> {
        return try {
            val docRef = firestore.collection("pedidos").add(pedido).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun actualizarEstadoPedido(pedidoId: String, nuevoEstado: String): Result<Unit> {
        return try {
            firestore.collection("pedidos")
                .document(pedidoId)
                .update("estado", nuevoEstado)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
