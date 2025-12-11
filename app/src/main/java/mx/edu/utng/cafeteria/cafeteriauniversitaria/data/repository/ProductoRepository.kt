package mx.edu.utng.cafeteria.cafeteriauniversitaria.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model.Producto
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import android.net.Uri

class ProductoRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    fun getProductos(): Flow<List<Producto>> = callbackFlow {
        val listener = firestore.collection("productos")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val productos = snapshot?.documents?.mapNotNull {
                    it.toObject(Producto::class.java)?.copy(id = it.id)
                } ?: emptyList()
                trySend(productos)
            }
        awaitClose { listener.remove() }
    }

    suspend fun getProductoById(id: String): Result<Producto> {
        return try {
            val snapshot = firestore.collection("productos").document(id).get().await()
            val producto = snapshot.toObject(Producto::class.java)?.copy(id = snapshot.id)
            if (producto != null) {
                Result.success(producto)
            } else {
                Result.failure(Exception("Producto no encontrado"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun agregarProducto(producto: Producto, imagenUri: Uri?): Result<String> {
        return try {
            var imagenUrl = ""

            // Subir imagen si existe
            if (imagenUri != null) {
                val storageRef = storage.reference.child("productos/${System.currentTimeMillis()}.jpg")
                storageRef.putFile(imagenUri).await()
                imagenUrl = storageRef.downloadUrl.await().toString()
            }

            val productoConImagen = producto.copy(imagenUrl = imagenUrl)
            val docRef = firestore.collection("productos").add(productoConImagen).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun actualizarProducto(id: String, producto: Producto): Result<Unit> {
        return try {
            firestore.collection("productos").document(id).set(producto).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun eliminarProducto(id: String): Result<Unit> {
        return try {
            firestore.collection("productos").document(id).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}