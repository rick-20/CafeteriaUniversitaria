# CAFETERIA UNIVERSITARIA
una app móvil (Android) con el proposito de que estudiantes puedan visualizar el menú de su cafetería, puedan
agregar productos al carrito de compras y realizar pedidos a la cafetería de su institución, así minimizando
el tiempo de espera en las filas.

# PUNTOS/APARTADOS CLAVE
1. Menú o Home - se muestran los productos disponibles en la cafetería.
2. Carrito de compras - muestra los productos y cantidades agregadas al mismo, permitiendo hacer el pedido y/o eliminar un producto.
3. Pedidos - Muestra al alumno sus pedidos y su estado (Pendiente, En proceso, Completado, Cancelado)
4. Perfíl - Muestra el nombre, correo, rol y número celular del alumno.
https://github.com/user-attachments/assets/20a42fe1-2ceb-436f-b66b-3722197f5a26

# REQUISITOS
* SDK de Android versión 36
* Uso de Firebase Firestore (o acceso a la base remota)

# INSTALACIÓN Paso a Paso 
# Arquitectura del Proyecto
<img width="519" height="879" alt="image" src="https://github.com/user-attachments/assets/e4e18739-c68e-4bcf-935d-66adf73ea674" />
<img width="461" height="841" alt="image" src="https://github.com/user-attachments/assets/3c4b96b6-afd0-4f51-83bc-ab7a22c0cf93" />

¿Por qué esta estructura?: Nos sirve para poder tener de manera ordenada cada parte de nuestro código, por lo que así nos permitirá poder visualizar cada componente, Screen, etc.

# Configuración inicial
1. Archivo libs.version.toml
```
[versions]
agp = "8.13.1"
kotlin = "2.0.21"
coreKtx = "1.17.0"
junit = "4.13.2"
junitVersion = "1.3.0"
espressoCore = "3.7.0"
lifecycleRuntimeKtx = "2.10.0"
activityCompose = "1.12.0"
composeBom = "2025.11.01"
firebaseAi = "17.6.0"
firebaseFirestoreKtx = "26.0.2"
googleServices = "4.4.4"
firebaseBom = "34.6.0"
firebaseStorageKtx = "21.0.0"


[libraries]
androidx-core-ktx = { group = "androidx.core", name = "core-ktx", version.ref = "coreKtx" }
junit = { group = "junit", name = "junit", version.ref = "junit" }
androidx-junit = { group = "androidx.test.ext", name = "junit", version.ref = "junitVersion" }
androidx-espresso-core = { group = "androidx.test.espresso", name = "espresso-core", version.ref = "espressoCore" }
androidx-lifecycle-runtime-ktx = { group = "androidx.lifecycle", name = "lifecycle-runtime-ktx", version.ref = "lifecycleRuntimeKtx" }
androidx-activity-compose = { group = "androidx.activity", name = "activity-compose", version.ref = "activityCompose" }
androidx-compose-bom = { group = "androidx.compose", name = "compose-bom", version.ref = "composeBom" }
androidx-compose-ui = { group = "androidx.compose.ui", name = "ui" }
androidx-compose-ui-graphics = { group = "androidx.compose.ui", name = "ui-graphics" }
androidx-compose-ui-tooling = { group = "androidx.compose.ui", name = "ui-tooling" }
androidx-compose-ui-tooling-preview = { group = "androidx.compose.ui", name = "ui-tooling-preview" }
androidx-compose-ui-test-manifest = { group = "androidx.compose.ui", name = "ui-test-manifest" }
androidx-compose-ui-test-junit4 = { group = "androidx.compose.ui", name = "ui-test-junit4" }
androidx-compose-material3 = { group = "androidx.compose.material3", name = "material3" }
firebase-ai = { group = "com.google.firebase", name = "firebase-ai", version.ref = "firebaseAi" }
firebase-bom = { module = "com.google.firebase:firebase-bom", version.ref = "firebaseBom" }
firebase-firestore-ktx = { module = "com.google.firebase:firebase-firestore" }
firebase-analytics = { module = "com.google.firebase:firebase-analytics" }
firebase-storage-ktx = { group = "com.google.firebase", name = "firebase-storage-ktx", version.ref = "firebaseStorageKtx" }

[plugins]
android-application = { id = "com.android.application", version.ref = "agp" }
kotlin-android = { id = "org.jetbrains.kotlin.android", version.ref = "kotlin" }
kotlin-compose = { id = "org.jetbrains.kotlin.plugin.compose", version.ref = "kotlin" }
google-services = { id = "com.google.gms.google-services", version.ref = "googleServices" }

2. Archivo build.gradle.kts (Module: app)
Este archivo es la lista de materiales que se usarán en el proyecto
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.services)
}

android {
    namespace = "mx.edu.utng.cafeteria.cafeteriauniversitaria"
    compileSdk {
        version = release(36)
    }

defaultConfig {
        applicationId = "mx.edu.utng.cafeteria.cafeteriauniversitaria"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "2.0"
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    Firebase (BoM)
    implementation("com.google.firebase:firebase-storage-ktx")
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.firebase.analytics)
    implementation("com.google.firebase:firebase-auth")
 /   
    AndroidX / Compose (compatibles con SDK 36)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
/
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.firebase.storage.ktx)
/
    Tests
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
/
    Debug
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
/
    // --- Jetpack Compose ---
    implementation(platform("androidx.compose:compose-bom:2024.10.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.activity:activity-compose:1.9.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")
/
    // --- Navegación ---
    implementation("androidx.navigation:navigation-compose:2.8.2")
/
    // --- Retrofit para llamadas HTTP ---
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
/
    // --- Corrutinas ---
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
/
    // --- Serialización JSON ---
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
/
    // --- Core Android ---
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
/
    //Iconos
    implementation("androidx.compose.material:material-icons-extended:1.3.1")
    testImplementation(kotlin("test"))
}
```
Explicación detallada:
namespace = es el nombre de la app
compileSdk = 36: La versión de Android que se usa para compilar
minSdk = 24: La app funcionará desde Android 7.0 en adelante
buildFeatures { compose = true }: Le decimos a Android que usaremos Jetpack Compose

# Código completo (Todos los archivos)
  # Capa de DATOS
  # data/model
La carpeta model nos sirve para crear las tablas de los medelos a usar, ejemplo: pedido, producto, usuario, etc..
<img width="211" height="295" alt="image" src="https://github.com/user-attachments/assets/7f2b22fa-916b-47d1-a567-43320a6f81a2" />

data/model/DetallePedido.kt
```
package mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model

data class DetallePedido(
    val id_producto: String = "",
    val nombreProducto: String = "",
    val cantidad: Int = 0,
    val precio_unitario: Double = 0.0,
    val subtotal: Double = 0.0
)
```
El detalle pedido nos sirve para declarar los atributos del mismo, los cuales usaremos para almacenar los datos correspondientes.

data/model/ItemCarrito.kt
```
package mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model

data class ItemCarrito(
    val producto: Producto,
    var cantidad: Int
) {
    val subtotal: Double
        get() = producto.precio * cantidad
}
```

data/model/Pedido.kt
```
package mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model

import com.google.firebase.Timestamp

data class Pedido(
    val id: String = "",
    val id_usuario: String = "",
    val fecha: Timestamp = Timestamp.now(),
    val total: Double = 0.0,
    val estado: String = "pendiente", // pendiente, en_proceso, completado, cancelado
    val detalles: List<DetallePedido> = emptyList()
)
```

data/model/Producto.kt
```
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
```

data/model/Promocion.kt
```
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
```

data/model/Usuario.kt
```
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
```
  # data/repository
Este paquete se utilizara para crear los repositorios, estos nos servirán para organizar y obtener el acceso a los datos de la app, facilitando la prueba y la moduladidad.

data/repository/AuthRepository.kt
```
package mx.edu.utng.cafeteria.cafeteriauniversitaria.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model.Usuario
import kotlinx.coroutines.tasks.await

/**
 * Repositorio encargado de la autenticación de usuarios
 * y de la gestión de datos del usuario en Firebase.
 */
class AuthRepository {

    /** Instancia de autenticación de Firebase */
    private val auth = FirebaseAuth.getInstance()

    /** Instancia de Firestore para manejo de usuarios */
    private val firestore = FirebaseFirestore.getInstance()

    /**
     * Obtiene el usuario actualmente autenticado
     */
    val currentUser: FirebaseUser?
        get() = auth.currentUser

    /**
     * Inicia sesión con correo y contraseña
     * @param email correo del usuario
     * @param password contraseña del usuario
     * @return Resultado con el usuario autenticado o error
     */
    suspend fun login(email: String, password: String): Result<FirebaseUser> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Result.success(result.user!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Registra un nuevo usuario en Firebase Authentication
     * y guarda sus datos en Firestore
     * @param email correo del usuario
     * @param password contraseña
     * @param usuario objeto Usuario con los datos
     */
    suspend fun register(
        email: String,
        password: String,
        usuario: Usuario
    ): Result<FirebaseUser> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = result.user!!.uid

            // Se guarda el usuario en la colección "usuarios"
            val nuevoUsuario = usuario.copy(id = uid, correo = email)
            firestore.collection("usuarios")
                .document(uid)
                .set(nuevoUsuario)
                .await()

            Result.success(result.user!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Obtiene los datos del usuario desde Firestore
     * @param uid ID del usuario
     */
    suspend fun getUserData(uid: String): Result<Usuario> {
        return try {
            val snapshot = firestore.collection("usuarios")
                .document(uid)
                .get()
                .await()

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

    /**
     * Cierra la sesión del usuario actual
     */
    fun logout() {
        auth.signOut()
    }
}
```

data/repository/PedidoRepository.kt
```
package mx.edu.utng.cafeteria.cafeteriauniversitaria.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model.Pedido
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

/**
 * Repositorio encargado de manejar los pedidos
 * realizados por los usuarios en la aplicación.
 */
class PedidoRepository {

    /** Instancia de Firestore */
    private val firestore = FirebaseFirestore.getInstance()

    /**
     * Obtiene los pedidos de un usuario específico
     * ordenados por fecha descendente
     */
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

    /**
     * Obtiene todos los pedidos (modo administrador)
     */
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

    /**
     * Crea un nuevo pedido en Firestore
     */
    suspend fun crearPedido(pedido: Pedido): Result<String> {
        return try {
            val docRef = firestore.collection("pedidos")
                .add(pedido)
                .await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Actualiza el estado de un pedido
     */
    suspend fun actualizarEstadoPedido(
        pedidoId: String,
        nuevoEstado: String
    ): Result<Unit> {
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
```

data/repository/ProductoRepository.kt
```
package mx.edu.utng.cafeteria.cafeteriauniversitaria.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model.Producto
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import android.net.Uri

/**
 * Repositorio encargado de la gestión de productos
 * de la cafetería.
 */
class ProductoRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    /**
     * Obtiene todos los productos en tiempo real
     */
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

    /**
     * Obtiene un producto por su ID
     */
    suspend fun getProductoById(id: String): Result<Producto> {
        return try {
            val snapshot = firestore.collection("productos")
                .document(id)
                .get()
                .await()

            val producto = snapshot.toObject(Producto::class.java)
                ?.copy(id = snapshot.id)

            if (producto != null) {
                Result.success(producto)
            } else {
                Result.failure(Exception("Producto no encontrado"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Agrega un nuevo producto con imagen opcional
     */
    suspend fun agregarProducto(
        producto: Producto,
        imagenUri: Uri?
    ): Result<String> {
        return try {
            var imagenUrl = ""

            if (imagenUri != null) {
                val storageRef = storage.reference
                    .child("productos/${System.currentTimeMillis()}.jpg")

                storageRef.putFile(imagenUri).await()
                imagenUrl = storageRef.downloadUrl.await().toString()
            }

            val productoConImagen = producto.copy(imagenUrl = imagenUrl)
            val docRef = firestore.collection("productos")
                .add(productoConImagen)
                .await()

            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Actualiza un producto existente
     */
    suspend fun actualizarProducto(
        id: String,
        producto: Producto
    ): Result<Unit> {
        return try {
            firestore.collection("productos")
                .document(id)
                .set(producto)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Elimina un producto
     */
    suspend fun eliminarProducto(id: String): Result<Unit> {
        return try {
            firestore.collection("productos")
                .document(id)
                .delete()
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

```

data/repository/PromocionRepository.kt
```
package mx.edu.utng.cafeteria.cafeteriauniversitaria.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model.Promocion
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

/**
 * Repositorio encargado de manejar todas las operaciones
 * relacionadas con la colección "promociones" en Firebase Firestore
 */
class PromocionRepository {

    // Instancia de Firestore
    private val firestore = FirebaseFirestore.getInstance()

    /**
     * Obtiene la lista de promociones en tiempo real
     * usando Flow para escuchar cambios en Firestore
     */
    fun getPromociones(): Flow<List<Promocion>> = callbackFlow {

        // Listener que escucha cambios en la colección "promociones"
        val listener = firestore.collection("promociones")
            .addSnapshotListener { snapshot, error ->

                // Si ocurre un error se cierra el flujo
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                // Convierte los documentos de Firestore a objetos Promocion
                val promociones = snapshot?.documents?.mapNotNull {
                    it.toObject(Promocion::class.java)?.copy(id = it.id)
                } ?: emptyList()

                // Envía la lista de promociones al Flow
                trySend(promociones)
            }

        // Se elimina el listener cuando el Flow deja de usarse
        awaitClose { listener.remove() }
    }

    /**
     * Agrega una nueva promoción a Firestore
     * @param promocion objeto Promocion a guardar
     * @return Result con el id del documento creado
     */
    suspend fun agregarPromocion(promocion: Promocion): Result<String> {
        return try {
            // Agrega la promoción a la colección
            val docRef = firestore.collection("promociones")
                .add(promocion)
                .await()

            // Retorna el id generado por Firestore
            Result.success(docRef.id)
        } catch (e: Exception) {
            // Retorna el error en caso de falla
            Result.failure(e)
        }
    }

    /**
     * Actualiza una promoción existente
     * @param id id del documento a actualizar
     * @param promocion nuevos datos de la promoción
     */
    suspend fun actualizarPromocion(id: String, promocion: Promocion): Result<Unit> {
        return try {
            // Reemplaza los datos del documento con el id indicado
            firestore.collection("promociones")
                .document(id)
                .set(promocion)
                .await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Elimina una promoción de Firestore
     * @param id id del documento a eliminar
     */
    suspend fun eliminarPromocion(id: String): Result<Unit> {
        return try {
            // Borra el documento con el id indicado
            firestore.collection("promociones")
                .document(id)
                .delete()
                .await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

```

# Capa de Interfaz de Usuario (UI)
# ui/components
ui/components/ProductoCard.kt
```
package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model.Producto

/**
 * Componente que muestra la información de un producto
 * en forma de tarjeta (Card)
 */
@Composable
fun ProductoCard(
    producto: Producto,       // Producto a mostrar
    onClick: () -> Unit,       // Acción al seleccionar la tarjeta
    onAddToCart: () -> Unit    // Acción para agregar el producto al carrito
) {

    // Tarjeta principal del producto
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()    // Ocupa todo el ancho disponible
            .height(250.dp)    // Altura fija de la tarjeta
    ) {

        // Contenedor vertical para organizar el contenido
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            // Sección de información del producto
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp) // Espaciado interno
            ) {

                // Nombre del producto
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,                    // Máximo una línea
                    overflow = TextOverflow.Ellipsis // "..." si el texto es largo
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Fila para precio y botón de carrito
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // Precio del producto
                    Text(
                        text = "$${producto.precio}",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )

                    // Botón para agregar al carrito
                    IconButton(
                        onClick = { onAddToCart() },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            Icons.Default.AddShoppingCart,
                            contentDescription = "Agregar al carrito",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

```

ui/components/PromocionesCard.kt
```
package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model.Promocion

/**
 * Componente que muestra la información de una promoción
 * dentro de una tarjeta (Card)
 */
@Composable
fun PromocionesCard(
    promocion: Promocion // Promoción a mostrar
) {

    // Tarjeta principal de la promoción
    Card(
        modifier = Modifier.fillMaxWidth(), // Ocupa todo el ancho disponible
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer // Color de fondo
        )
    ) {

        // Contenedor vertical para el contenido
        Column(
            modifier = Modifier.padding(12.dp) // Espaciado interno
        ) {

            // Nombre de la promoción
            Text(
                text = promocion.nombre,
                style = MaterialTheme.typography.titleMedium
            )

            // Descripción de la promoción
            Text(
                text = promocion.descripcion,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

```
# ui/navegation
Pasamos al apartado de UI con navegación

ui/navegation/NavGraph.kt
```
package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.navegation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.screen.auth.LoginScreen
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.screen.auth.RegisterScreen
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.screen.usuario.*
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.screen.admin.*
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel.AuthState
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel.AuthViewModel
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel.CarritoViewModel

/**
 * Gráfico de navegación principal de la aplicación
 * Controla las rutas y pantallas según el rol del usuario
 */
@Composable
fun NavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel = viewModel()
) {

    // Estado de autenticación
    val authState by authViewModel.authState.collectAsState()

    // Usuario actual autenticado
    val currentUser by authViewModel.currentUser.collectAsState()

    // Determina la pantalla inicial según el estado y rol
    val startDestination = when (authState) {
        is AuthState.Authenticated -> {
            if (currentUser?.rol == "admin")
                Screen.AdminHome.route
            else
                Screen.Home.route
        }
        else -> Screen.Login.route
    }

    // ViewModel del carrito (compartido entre pantallas)
    val carritoViewModel: CarritoViewModel = viewModel()

    // Host de navegación
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        /* =======================
           Pantallas de Autenticación
           ======================= */

        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                },
                onLoginSuccess = { usuario ->
                    // Redirige según el rol
                    if (usuario.rol == "admin") {
                        navController.navigate(Screen.AdminHome.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    } else {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onNavigateToLogin = {
                    navController.popBackStack()
                },
                onRegisterSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                }
            )
        }

        /* =======================
           Pantallas de Usuario
           ======================= */

        composable(Screen.Home.route) {
            HomeScreen(
                carritoViewModel = carritoViewModel,
                onNavigateToProducto = { productoId ->
                    navController.navigate(
                        Screen.ProductoDetalle.createRoute(productoId)
                    )
                },
                onNavigateToCarrito = {
                    navController.navigate(Screen.Carrito.route)
                },
                onNavigateToPedidos = {
                    navController.navigate(Screen.Pedidos.route)
                },
                onNavigateToPerfil = {
                    navController.navigate(Screen.Perfil.route)
                }
            )
        }

        composable(
            route = Screen.ProductoDetalle.route,
            arguments = listOf(
                navArgument("productoId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            // Obtiene el id del producto enviado por navegación
            val productoId =
                backStackEntry.arguments?.getString("productoId") ?: ""

            ProductoDetalleScreen(
                productoId = productoId,
                carritoViewModel = carritoViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Carrito.route) {
            CarritoScreen(
                carritoViewModel = carritoViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onFinalizarCompra = {
                    navController.navigate(Screen.Pedidos.route)
                }
            )
        }

        composable(Screen.Pedidos.route) {
            PedidosScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Perfil.route) {
            PerfilScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onLogout = {
                    authViewModel.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        /* =======================
           Pantallas de Administrador
           ======================= */

        composable(Screen.AdminHome.route) {
            AdminHomeScreen(
                onNavigateToProductos = {
                    navController.navigate(Screen.ProductosCrud.route)
                },
                onNavigateToPromociones = {
                    navController.navigate(Screen.PromocionesAdmin.route)
                },
                onNavigateToPedidos = {
                    navController.navigate(Screen.PedidosAdmin.route)
                },
                onLogout = {
                    authViewModel.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.ProductosCrud.route) {
            ProductosCrudScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.PromocionesAdmin.route) {
            PromocionesScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.PedidosAdmin.route) {
            PedidosAdminScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

```

ui/navegation/Screen.kt
```
package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.navegation

/**
 * Clase sellada que define todas las rutas de navegación
 * utilizadas en la aplicación
 */
sealed class Screen(val route: String) {

    /* =======================
       Pantallas de Autenticación
       ======================= */

    // Pantalla de inicio de sesión
    object Login : Screen("login")

    // Pantalla de registro de usuarios
    object Register : Screen("register")


    /* =======================
       Pantallas de Usuario
       ======================= */

    // Pantalla principal del usuario
    object Home : Screen("home")

    // Pantalla de detalle de un producto
    // Recibe como parámetro el id del producto
    object ProductoDetalle : Screen("producto/{productoId}") {

        // Construye la ruta completa con el id del producto
        fun createRoute(productoId: String) = "producto/$productoId"
    }

    // Pantalla del carrito de compras
    object Carrito : Screen("carrito")

    // Pantalla de pedidos del usuario
    object Pedidos : Screen("pedidos")

    // Pantalla de perfil del usuario
    object Perfil : Screen("perfil")


    /* =======================
       Pantallas de Administrador
       ======================= */

    // Pantalla principal del administrador
    object AdminHome : Screen("admin/home")

    // Pantalla para administrar productos
    object ProductosCrud : Screen("admin/productos")

    // Pantalla para administrar promociones
    object PromocionesAdmin : Screen("admin/promociones")

    // Pantalla para administrar pedidos
    object PedidosAdmin : Screen("admin/pedidos")
}

```

# Pasamos a las Screen o Pantallas que serán mostradas al usuario
# screen/admin
Primero con las vistas correspondientes a los administradores

screen/admin/AdminHomeScreen.kt
Esta Screen se encargará de mostrar el "Inicio" de la vista del admin
```
package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.screen.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel.AuthViewModel

/**
 * Pantalla principal del administrador
 * Muestra el panel con accesos a las funciones principales
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminHomeScreen(
    onNavigateToProductos: () -> Unit,     // Navegar a gestión de productos
    onNavigateToPromociones: () -> Unit,   // Navegar a gestión de promociones
    onNavigateToPedidos: () -> Unit,       // Navegar a gestión de pedidos
    onLogout: () -> Unit,                  // Cerrar sesión
    authViewModel: AuthViewModel = viewModel()
) {

    // Usuario autenticado (admin)
    val currentUser by authViewModel.currentUser.collectAsState()

    // Estructura base de la pantalla
    Scaffold(
        topBar = {

            // Barra superior con título y botón de cerrar sesión
            TopAppBar(
                title = { Text("Panel de Administración") },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(
                            Icons.Default.ExitToApp,
                            contentDescription = "Cerrar Sesión"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        // Contenido principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            /* =======================
               Tarjeta de información del admin
               ======================= */

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // Ícono del administrador
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Admin",
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    // Nombre y rol del admin
                    Column {
                        Text(
                            text = currentUser?.nombre ?: "Administrador",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = "Administrador del Sistema",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            /* =======================
               Menú de opciones del administrador
               ======================= */

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // Opción: Productos
                item {
                    AdminMenuCard(
                        icon = Icons.Default.Fastfood,
                        title = "Productos",
                        description = "Gestionar menú",
                        onClick = onNavigateToProductos
                    )
                }

                // Opción: Promociones
                item {
                    AdminMenuCard(
                        icon = Icons.Default.LocalOffer,
                        title = "Promociones",
                        description = "Gestionar ofertas",
                        onClick = onNavigateToPromociones
                    )
                }

                // Opción: Pedidos
                item {
                    AdminMenuCard(
                        icon = Icons.Default.Receipt,
                        title = "Pedidos",
                        description = "Ver todos los pedidos",
                        onClick = onNavigateToPedidos
                    )
                }
            }
        }
    }
}

/**
 * Tarjeta reutilizable para las opciones del menú del administrador
 */
@Composable
fun AdminMenuCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector, // Ícono del menú
    title: String,                                        // Título
    description: String,                                  // Descripción
    onClick: () -> Unit,                                  // Acción al presionar
    enabled: Boolean = true                               // Habilitar / deshabilitar
) {

    // Tarjeta del menú
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f), // Mantiene forma cuadrada
        enabled = enabled
    ) {

        // Contenido de la tarjeta
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Ícono
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(48.dp),
                tint = if (enabled)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Título
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = if (enabled)
                    MaterialTheme.colorScheme.onSurface
                else
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
            )

            // Descripción
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

```

screen/admin/PedidosAdminScreen.kt
Vista de los pedidos de admin, es decir, aquí le aparecerán los pedidos de los alumnos
```
package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.screen.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model.Pedido
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel.PedidoViewModel
import java.text.SimpleDateFormat
import java.util.*

/**
 * Pantalla de administración para visualizar
 * y gestionar todos los pedidos del sistema
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PedidosAdminScreen(
    onNavigateBack: () -> Unit,                 // Regresar a la pantalla anterior
    pedidoViewModel: PedidoViewModel = viewModel()
) {

    // Lista de pedidos obtenida del ViewModel
    val pedidos by pedidoViewModel.pedidos.collectAsState()

    // Carga todos los pedidos al iniciar la pantalla
    LaunchedEffect(Unit) {
        pedidoViewModel.cargarTodosPedidos()
    }

    // Estructura principal de la pantalla
    Scaffold(
        topBar = {

            // Barra superior con botón de regreso
            TopAppBar(
                title = { Text("Todos los Pedidos") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        // Si no hay pedidos, se muestra un mensaje
        if (pedidos.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("No hay pedidos registrados")
            }

        } else {

            // Lista de pedidos
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(pedidos) { pedido ->
                    PedidoAdminCard(
                        pedido = pedido,
                        onUpdateEstado = { nuevoEstado ->
                            pedidoViewModel.actualizarEstado(
                                pedido.id,
                                nuevoEstado
                            )
                        }
                    )
                }
            }
        }
    }
}

/**
 * Tarjeta que muestra la información de un pedido
 * y permite actualizar su estado
 */
@Composable
fun PedidoAdminCard(
    pedido: Pedido,                     // Pedido a mostrar
    onUpdateEstado: (String) -> Unit     // Acción para cambiar el estado
) {

    // Formato de fecha y hora
    val dateFormat =
        SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

    val fechaStr = dateFormat.format(pedido.fecha.toDate())

    // Estado para mostrar u ocultar los detalles
    var expanded by remember { mutableStateOf(false) }

    // Estado para mostrar el diálogo de cambio de estado
    var showEstadoDialog by remember { mutableStateOf(false) }

    // Color según el estado del pedido
    val estadoColor = when (pedido.estado) {
        "pendiente" -> MaterialTheme.colorScheme.tertiary
        "en_proceso" -> MaterialTheme.colorScheme.primary
        "completado" -> Color(0xFF4CAF50)
        else -> MaterialTheme.colorScheme.error
    }

    // Tarjeta principal del pedido
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            /* =======================
               Encabezado del pedido
               ======================= */

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                // Información básica del pedido
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Pedido #${pedido.id.take(8)}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = fechaStr,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }

                // Chip para mostrar y cambiar el estado
                Surface(
                    color = estadoColor.copy(alpha = 0.2f),
                    shape = MaterialTheme.shapes.small,
                    onClick = { showEstadoDialog = true }
                ) {
                    Row(
                        modifier = Modifier.padding(
                            horizontal = 12.dp,
                            vertical = 6.dp
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = pedido.estado
                                .replace("_", " ")
                                .capitalize(Locale.ROOT),
                            color = estadoColor,
                            style = MaterialTheme.typography.labelMedium
                        )
                        Icon(
                            Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = estadoColor
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            /* =======================
               Total del pedido
               ======================= */

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Total:", style = MaterialTheme.typography.titleMedium)
                Text(
                    "$${String.format("%.2f", pedido.total)}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            /* =======================
               Botón de detalles
               ======================= */

            TextButton(
                onClick = { expanded = !expanded },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(if (expanded) "Ver menos" else "Ver detalles")
                Icon(
                    if (expanded)
                        Icons.Default.ExpandLess
                    else
                        Icons.Default.ExpandMore,
                    contentDescription = null
                )
            }

            /* =======================
               Detalles del pedido
               ======================= */

            if (expanded) {
                Divider(modifier = Modifier.padding(vertical = 8.dp))

                pedido.detalles.forEach { detalle ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("${detalle.cantidad}x ${detalle.nombreProducto}")
                        Text("$${String.format("%.2f", detalle.subtotal)}")
                    }
                }
            }
        }
    }

    /* =======================
       Diálogo para cambiar estado
       ======================= */

    if (showEstadoDialog) {
        AlertDialog(
            onDismissRequest = { showEstadoDialog = false },
            title = { Text("Cambiar Estado") },
            text = {
                Column {
                    listOf(
                        "pendiente" to "Pendiente",
                        "en_proceso" to "En Proceso",
                        "completado" to "Completado",
                        "cancelado" to "Cancelado"
                    ).forEach { (estado, nombre) ->
                        TextButton(
                            onClick = {
                                onUpdateEstado(estado)
                                showEstadoDialog = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(nombre)
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showEstadoDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

```

screen/admin/ProductosCrudScreen.kt
```
package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.screen.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model.Producto
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel.ProductoViewModel

/**
 * Pantalla de administración para realizar
 * operaciones CRUD sobre los productos
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductosCrudScreen(
    onNavigateBack: () -> Unit,                     // Regresar a la pantalla anterior
    productoViewModel: ProductoViewModel = viewModel()
) {

    // Lista de productos obtenida del ViewModel
    val productos by productoViewModel.productos.collectAsState()

    // Controla la visibilidad del diálogo
    var showDialog by remember { mutableStateOf(false) }

    // Producto seleccionado para editar (null si es nuevo)
    var selectedProducto by remember { mutableStateOf<Producto?>(null) }

    // Estructura base de la pantalla
    Scaffold(
        topBar = {

            // Barra superior con botón de regreso
            TopAppBar(
                title = { Text("Gestionar Productos") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        },

        // Botón flotante para agregar producto
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    selectedProducto = null
                    showDialog = true
                }
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Agregar Producto"
                )
            }
        }
    ) { paddingValues ->

        // Lista de productos
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(productos) { producto ->
                ProductoAdminCard(
                    producto = producto,

                    // Editar producto
                    onEdit = {
                        selectedProducto = producto
                        showDialog = true
                    },

                    // Eliminar producto
                    onDelete = {
                        productoViewModel.eliminarProducto(producto.id)
                    }
                )
            }
        }
    }

    // Diálogo para agregar o editar producto
    if (showDialog) {
        ProductoDialog(
            producto = selectedProducto,
            onDismiss = { showDialog = false },
            onConfirm = { producto ->

                // Decide si se actualiza o se agrega
                if (selectedProducto != null) {
                    productoViewModel.actualizarProducto(
                        selectedProducto!!.id,
                        producto
                    )
                } else {
                    productoViewModel.agregarProducto(producto, null)
                }

                showDialog = false
            }
        )
    }
}

/**
 * Tarjeta que muestra la información de un producto
 * con opciones para editar o eliminar
 */
@Composable
fun ProductoAdminCard(
    producto: Producto,        // Producto a mostrar
    onEdit: () -> Unit,        // Acción para editar
    onDelete: () -> Unit       // Acción para eliminar
) {

    // Controla el diálogo de confirmación de eliminación
    var showDeleteDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Información del producto
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "$${producto.precio}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = if (producto.disponible)
                        "Disponible"
                    else
                        "No disponible",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (producto.disponible)
                        Color(0xFF4CAF50)
                    else
                        MaterialTheme.colorScheme.error
                )
            }

            // Botones de acciones
            Row {
                IconButton(onClick = onEdit) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Editar",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                IconButton(onClick = { showDeleteDialog = true }) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }

    // Diálogo de confirmación para eliminar producto
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Eliminar Producto") },
            text = {
                Text("¿Estás seguro de que quieres eliminar ${producto.nombre}?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDelete()
                        showDeleteDialog = false
                    }
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

/**
 * Diálogo reutilizable para agregar o editar un producto
 */
@Composable
fun ProductoDialog(
    producto: Producto?,               // Producto a editar (null si es nuevo)
    onDismiss: () -> Unit,              // Cerrar diálogo
    onConfirm: (Producto) -> Unit       // Guardar producto
) {

    // Estados de los campos
    var nombre by remember { mutableStateOf(producto?.nombre ?: "") }
    var descripcion by remember { mutableStateOf(producto?.descripcion ?: "") }
    var precio by remember { mutableStateOf(producto?.precio?.toString() ?: "") }
    var categoria by remember { mutableStateOf(producto?.categoria ?: "") }
    var disponible by remember { mutableStateOf(producto?.disponible ?: true) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                if (producto != null)
                    "Editar Producto"
                else
                    "Nuevo Producto"
            )
        },
        text = {

            // Contenido del formulario
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )

                OutlinedTextField(
                    value = precio,
                    onValueChange = { precio = it },
                    label = { Text("Precio") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                        keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal
                    )
                )

                OutlinedTextField(
                    value = categoria,
                    onValueChange = { categoria = it },
                    label = { Text("Categoría") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                // Checkbox de disponibilidad
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = disponible,
                        onCheckedChange = { disponible = it }
                    )
                    Text("Disponible")
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {

                    // Convierte el precio a Double
                    val precioDouble = precio.toDoubleOrNull() ?: 0.0

                    // Crea el objeto Producto
                    val nuevoProducto = Producto(
                        id = producto?.id ?: "",
                        nombre = nombre,
                        descripcion = descripcion,
                        precio = precioDouble,
                        disponible = disponible,
                        categoria = categoria,
                        imagenUrl = producto?.imagenUrl ?: ""
                    )

                    onConfirm(nuevoProducto)
                },
                enabled = nombre.isNotBlank() && precio.toDoubleOrNull() != null
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

```

screen/admin/PromocionesScreen.kt
```
package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.screen.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.Timestamp
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model.Promocion
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel.AdminViewModel
import java.text.SimpleDateFormat
import java.util.*

/**
 * Pantalla para administrar promociones
 * Permite crear, editar y eliminar promociones
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromocionesScreen(
    onNavigateBack: () -> Unit,                 // Regresa a la pantalla anterior
    adminViewModel: AdminViewModel = viewModel()
) {

    // Lista de promociones obtenida del ViewModel
    val promociones by adminViewModel.promociones.collectAsState()

    // Controla si se muestra el diálogo
    var showDialog by remember { mutableStateOf(false) }

    // Promoción seleccionada para editar
    var selectedPromocion by remember { mutableStateOf<Promocion?>(null) }

    Scaffold(
        topBar = {

            // Barra superior
            TopAppBar(
                title = { Text("Gestionar Promociones") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                }
            )
        },

        // Botón flotante para agregar promoción
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    selectedPromocion = null
                    showDialog = true
                }
            ) {
                Icon(Icons.Default.Add, "Agregar Promoción")
            }
        }
    ) { paddingValues ->

        // Mensaje si no hay promociones
        if (promociones.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("No hay promociones creadas")
            }
        } else {

            // Lista de promociones
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(promociones) { promocion ->
                    PromocionCard(
                        promocion = promocion,

                        // Editar promoción
                        onEdit = {
                            selectedPromocion = promocion
                            showDialog = true
                        },

                        // Eliminar promoción
                        onDelete = {
                            adminViewModel.eliminarPromocion(promocion.id)
                        }
                    )
                }
            }
        }
    }

    // Diálogo para crear o editar promoción
    if (showDialog) {
        PromocionDialog(
            promocion = selectedPromocion,
            onDismiss = { showDialog = false },
            onConfirm = { promocion ->

                // Decide si se actualiza o se agrega
                if (selectedPromocion != null) {
                    adminViewModel.actualizarPromocion(
                        selectedPromocion!!.id,
                        promocion
                    )
                } else {
                    adminViewModel.agregarPromocion(promocion)
                }

                showDialog = false
            }
        )
    }
}

/**
 * Tarjeta que muestra la información de una promoción
 */
@Composable
fun PromocionCard(
    promocion: Promocion,   // Promoción a mostrar
    onEdit: () -> Unit,     // Acción para editar
    onDelete: () -> Unit    // Acción para eliminar
) {

    // Controla el diálogo de confirmación de eliminación
    var showDeleteDialog by remember { mutableStateOf(false) }

    // Formato de fecha
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            // Información de la promoción
            Column(modifier = Modifier.weight(1f)) {

                Text(
                    text = promocion.nombre,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = promocion.descripcion,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Puntos requeridos
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${promocion.puntos_requeridos} puntos",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                // Rango de fechas
                Text(
                    text = "${dateFormat.format(promocion.fecha_inicio.toDate())} - " +
                           "${dateFormat.format(promocion.fecha_fin.toDate())}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }

            // Botones de acción
            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, "Editar",
                        tint = MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = { showDeleteDialog = true }) {
                    Icon(Icons.Default.Delete, "Eliminar",
                        tint = MaterialTheme.colorScheme.error)
                }
            }
        }
    }

    // Diálogo de confirmación para eliminar
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Eliminar Promoción") },
            text = {
                Text("¿Estás seguro de que quieres eliminar ${promocion.nombre}?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDelete()
                        showDeleteDialog = false
                    }
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

/**
 * Diálogo para crear o editar una promoción
 */
@Composable
fun PromocionDialog(
    promocion: Promocion?,            // Promoción a editar (null si es nueva)
    onDismiss: () -> Unit,
    onConfirm: (Promocion) -> Unit
) {

    // Estados de los campos
    var nombre by remember { mutableStateOf(promocion?.nombre ?: "") }
    var descripcion by remember { mutableStateOf(promocion?.descripcion ?: "") }
    var puntos by remember {
        mutableStateOf(promocion?.puntos_requeridos?.toString() ?: "")
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(if (promocion != null) "Editar Promoción" else "Nueva Promoción")
        },
        text = {

            // Formulario
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )

                OutlinedTextField(
                    value = puntos,
                    onValueChange = { puntos = it },
                    label = { Text("Puntos Requeridos") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                        keyboardType = androidx.compose.ui.text.input.KeyboardType.Number
                    )
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {

                    // Convierte puntos a entero
                    val puntosInt = puntos.toIntOrNull() ?: 0

                    // Crea el objeto Promocion
                    val nuevaPromocion = Promocion(
                        id = promocion?.id ?: "",
                        nombre = nombre,
                        descripcion = descripcion,
                        puntos_requeridos = puntosInt,
                        fecha_inicio = promocion?.fecha_inicio ?: Timestamp.now(),
                        fecha_fin = promocion?.fecha_fin ?: Timestamp.now()
                    )

                    onConfirm(nuevaPromocion)
                },
                enabled = nombre.isNotBlank() && puntos.toIntOrNull() != null
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

```

# screen/auth
Este paquete será la autenticación, donde se guardará el inicio, registro y cierre de sesión de los usuarios

screen/auth/LoginScreen.kt
```
package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.screen.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model.Usuario
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel.AuthState
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel.AuthViewModel

/**
 * Pantalla de inicio de sesión
 * Permite al usuario autenticarse con correo y contraseña
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit,        // Navega a registro
    onLoginSuccess: (Usuario) -> Unit,       // Se ejecuta cuando el login es exitoso
    authViewModel: AuthViewModel = viewModel()
) {

    // Estados locales de los campos
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Controla la visibilidad de la contraseña
    var passwordVisible by remember { mutableStateOf(false) }

    // Estado de autenticación desde el ViewModel
    val authState by authViewModel.authState.collectAsState()

    // Detecta cuando el usuario ya fue autenticado
    LaunchedEffect(authState) {
        if (authState is AuthState.Authenticated) {
            onLoginSuccess(
                (authState as AuthState.Authenticated).usuario
            )
        }
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.height(32.dp))

            // Título
            Text(
                text = "Cafetería Universitaria",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Subtítulo
            Text(
                text = "Bienvenido de vuelta",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )

            Spacer(modifier = Modifier.height(32.dp))

            /* ---------- Campo Email ---------- */
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                leadingIcon = { Icon(Icons.Default.Email, null) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            /* ---------- Campo Contraseña ---------- */
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                leadingIcon = { Icon(Icons.Default.Lock, null) },

                // Icono para mostrar u ocultar contraseña
                trailingIcon = {
                    IconButton(
                        onClick = { passwordVisible = !passwordVisible }
                    ) {
                        Icon(
                            imageVector = if (passwordVisible)
                                Icons.Default.Visibility
                            else
                                Icons.Default.VisibilityOff,
                            contentDescription = if (passwordVisible)
                                "Ocultar contraseña"
                            else
                                "Mostrar contraseña"
                        )
                    }
                },

                // Control de visibilidad
                visualTransformation =
                    if (passwordVisible)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation(),

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            /* ---------- Botón Iniciar Sesión ---------- */
            Button(
                onClick = {
                    if (email.isNotBlank() && password.isNotBlank()) {
                        authViewModel.login(email, password)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = authState !is AuthState.Loading
            ) {

                // Indicador de carga
                if (authState is AuthState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text(
                        text = "Iniciar Sesión",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            /* ---------- Enlace a registro ---------- */
            TextButton(onClick = onNavigateToRegister) {
                Text("¿No tienes cuenta? Regístrate")
            }

            /* ---------- Mensaje de error ---------- */
            if (authState is AuthState.Error) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = (authState as AuthState.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

```

screen/auth/RegistrerScreen.kt
```
package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.screen.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel.AuthState
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel.AuthViewModel

/**
 * Pantalla de registro de usuarios
 * Permite crear una cuenta nueva con datos básicos
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onNavigateToLogin: () -> Unit,   // Regresa al login
    onRegisterSuccess: () -> Unit,   // Se ejecuta al registrarse correctamente
    authViewModel: AuthViewModel = viewModel()
) {

    /* ---------- Estados de los campos ---------- */
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    /* ---------- Control de visibilidad ---------- */
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    // Estado de autenticación
    val authState by authViewModel.authState.collectAsState()

    // Scroll para pantallas pequeñas
    val scrollState = rememberScrollState()

    // Detecta cuando el registro fue exitoso
    LaunchedEffect(authState) {
        if (authState is AuthState.Authenticated) {
            onRegisterSuccess()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crear Cuenta") },
                navigationIcon = {
                    IconButton(onClick = onNavigateToLogin) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Únete a nosotros",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(24.dp))

            /* ---------- Nombre ---------- */
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre completo") },
                leadingIcon = { Icon(Icons.Default.Person, null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            /* ---------- Email ---------- */
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                leadingIcon = { Icon(Icons.Default.Email, null) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            /* ---------- Teléfono ---------- */
            OutlinedTextField(
                value = telefono,
                onValueChange = { telefono = it },
                label = { Text("Teléfono") },
                leadingIcon = { Icon(Icons.Default.Phone, null) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                ),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            /* ---------- Contraseña ---------- */
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                leadingIcon = { Icon(Icons.Default.Lock, null) },
                trailingIcon = {
                    IconButton(
                        onClick = { passwordVisible = !passwordVisible }
                    ) {
                        Icon(
                            imageVector = if (passwordVisible)
                                Icons.Default.Visibility
                            else
                                Icons.Default.VisibilityOff,
                            contentDescription = null
                        )
                    }
                },
                visualTransformation =
                    if (passwordVisible)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            /* ---------- Confirmar contraseña ---------- */
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirmar contraseña") },
                leadingIcon = { Icon(Icons.Default.Lock, null) },
                trailingIcon = {
                    IconButton(
                        onClick = { confirmPasswordVisible = !confirmPasswordVisible }
                    ) {
                        Icon(
                            imageVector = if (confirmPasswordVisible)
                                Icons.Default.Visibility
                            else
                                Icons.Default.VisibilityOff,
                            contentDescription = null
                        )
                    }
                },
                visualTransformation =
                    if (confirmPasswordVisible)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,

                // Marca error si no coinciden
                isError = confirmPassword.isNotEmpty() &&
                        password != confirmPassword
            )

            // Mensaje de error de contraseña
            if (confirmPassword.isNotEmpty() &&
                password != confirmPassword
            ) {
                Text(
                    text = "Las contraseñas no coinciden",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            /* ---------- Botón Registrar ---------- */
            Button(
                onClick = {
                    authViewModel.register(
                        email,
                        password,
                        nombre,
                        telefono,
                        ""
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = authState !is AuthState.Loading &&
                        nombre.isNotBlank() &&
                        email.isNotBlank() &&
                        telefono.isNotBlank() &&
                        password.isNotBlank() &&
                        password == confirmPassword
            ) {

                if (authState is AuthState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text(
                        text = "Registrarse",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            /* ---------- Error general ---------- */
            if (authState is AuthState.Error) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = (authState as AuthState.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

```

# Pasamos a las vistas de los usuarios (Clientes)
# screen/usuario
screen/usuario/CarritoScreen.kt
```
package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.screen.usuario

// Imports necesarios para Jetpack Compose y Material 3
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

// Modelos y ViewModels usados en la pantalla
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model.ItemCarrito
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel.AuthViewModel
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel.CarritoViewModel
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel.PedidoViewModel

// Se habilita Material3 experimental para usar componentes como TopAppBar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(
    // Función para regresar a la pantalla anterior
    onNavigateBack: () -> Unit,
    // Función que se ejecuta cuando se finaliza la compra
    onFinalizarCompra: () -> Unit,
    // ViewModel del carrito (se recibe por parámetro)
    carritoViewModel: CarritoViewModel,
    // ViewModel del pedido
    pedidoViewModel: PedidoViewModel = viewModel(),
    // ViewModel de autenticación
    authViewModel: AuthViewModel = viewModel()
) {
    // Lista de productos del carrito observada como estado
    val items by carritoViewModel.items.collectAsState()

    // Total del carrito
    val total = carritoViewModel.total

    // Estado que indica si el pedido ya fue creado
    val pedidoCreado by pedidoViewModel.pedidoCreado.collectAsState()

    // Usuario autenticado actualmente
    val currentUser by authViewModel.currentUser.collectAsState()

    // Efecto que se ejecuta cuando cambia el estado de pedidoCreado
    LaunchedEffect(pedidoCreado) {
        if (pedidoCreado) {
            // Limpia el carrito después de crear el pedido
            carritoViewModel.limpiarCarrito()
            // Resetea el estado del pedido
            pedidoViewModel.resetPedidoCreado()
            // Navega a la siguiente pantalla
            onFinalizarCompra()
        }
    }

    // Estructura principal de la pantalla
    Scaffold(
        // Barra superior
        topBar = {
            TopAppBar(
                title = { Text("Mi Carrito") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                }
            )
        },
        // Barra inferior con el total y el botón de compra
        bottomBar = {
            // Solo se muestra si hay productos en el carrito
            if (items.isNotEmpty()) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shadowElevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        // Fila que muestra el total
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Total:", style = MaterialTheme.typography.titleLarge)
                            Text(
                                "$${String.format("%.2f", total)}",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Botón para realizar el pedido
                        Button(
                            onClick = {
                                // Solo se crea el pedido si hay un usuario autenticado
                                currentUser?.let { user ->
                                    pedidoViewModel.crearPedido(user.id, items)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                        ) {
                            Text("Realizar Pedido")
                        }
                    }
                }
            }
        }
    ) { paddingValues ->

        // Vista cuando el carrito está vacío
        if (items.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "Tu carrito está vacío",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    TextButton(onClick = onNavigateBack) {
                        Text("Explorar productos")
                    }
                }
            }
        } else {
            // Lista de productos del carrito
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(items) { item ->
                    // Tarjeta individual por producto
                    CarritoItemCard(
                        item = item,
                        // Actualiza la cantidad del producto
                        onUpdateCantidad = { cantidad ->
                            carritoViewModel.actualizarCantidad(item.producto.id, cantidad)
                        },
                        // Elimina el producto del carrito
                        onEliminar = {
                            carritoViewModel.eliminarProducto(item.producto.id)
                        }
                    )
                }
            }
        }
    }
}

// Composable que representa un producto dentro del carrito
@Composable
fun CarritoItemCard(
    item: ItemCarrito,
    onUpdateCantidad: (Int) -> Unit,
    onEliminar: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Información del producto
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.producto.nombre,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "$${item.producto.precio} c/u",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Controles para modificar la cantidad
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = { onUpdateCantidad(item.cantidad - 1) },
                        enabled = item.cantidad > 1
                    ) {
                        Icon(Icons.Default.Remove, "Disminuir")
                    }

                    Text(
                        text = item.cantidad.toString(),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    IconButton(onClick = { onUpdateCantidad(item.cantidad + 1) }) {
                        Icon(Icons.Default.Add, "Aumentar")
                    }
                }
            }

            // Subtotal y botón eliminar
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "$${String.format("%.2f", item.subtotal)}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                IconButton(onClick = onEliminar) {
                    Icon(
                        Icons.Default.Delete,
                        "Eliminar",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

```

screen/usuario/HomeScreen.kt
```
package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.screen.usuario

// Imports necesarios para layouts, listas y grids en Compose
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items

// Icons de Material
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart

// Componentes Material 3
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

// Componentes personalizados
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.components.ProductoCard
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.components.PromocionesCard

// ViewModels usados en esta pantalla
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel.AdminViewModel
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel.CarritoViewModel
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel.ProductoViewModel

// Se habilita Material3 experimental para usar TopAppBar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    // Navega al detalle de un producto usando su ID
    onNavigateToProducto: (String) -> Unit,
    // Navega a la pantalla del carrito
    onNavigateToCarrito: () -> Unit,
    // Navega a la pantalla de pedidos
    onNavigateToPedidos: () -> Unit,
    // Navega al perfil del usuario
    onNavigateToPerfil: () -> Unit,
    // ViewModel de productos
    productoViewModel: ProductoViewModel = viewModel(),
    // ViewModel del carrito (se recibe por parámetro)
    carritoViewModel: CarritoViewModel,
    // ViewModel del administrador (promociones)
    adminViewModel: AdminViewModel = viewModel()
) {

    // Lista de promociones obtenidas del AdminViewModel
    val promociones by adminViewModel.promociones.collectAsState()

    // Lista de productos obtenida del ProductoViewModel
    val productos by productoViewModel.productos.collectAsState()

    // Estado de carga de productos
    val isLoading by productoViewModel.isLoading.collectAsState()

    // Cantidad total de productos en el carrito
    val cantidadCarrito by carritoViewModel.cantidadItems.collectAsState()

    // Estructura principal de la pantalla
    Scaffold(
        // Barra superior
        topBar = {
            TopAppBar(
                title = { Text("Cafetería Universitaria") },
                actions = {

                    // Ícono del carrito con badge para mostrar la cantidad
                    BadgedBox(
                        badge = {
                            if (cantidadCarrito > 0) {
                                Badge {
                                    Text(cantidadCarrito.toString())
                                }
                            }
                        }
                    ) {
                        IconButton(onClick = onNavigateToCarrito) {
                            Icon(Icons.Default.ShoppingCart, "Carrito")
                        }
                    }

                    // Botón para ir a pedidos
                    IconButton(onClick = onNavigateToPedidos) {
                        Icon(Icons.Default.ListAlt, "Pedidos")
                    }

                    // Botón para ir al perfil
                    IconButton(onClick = onNavigateToPerfil) {
                        Icon(Icons.Default.Person, "Perfil")
                    }
                }
            )
        }
    ) { paddingValues ->

        // Mientras cargan los productos se muestra un indicador
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {

            // Grid de productos (2 columnas)
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                // Sección de promociones (ocupa todo el ancho)
                if (promociones.isNotEmpty()) {
                    item(span = { GridItemSpan(2) }) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Promociones",
                                style = MaterialTheme.typography.titleLarge
                            )

                            // Se recorren y muestran las promociones
                            promociones.forEach { promo ->
                                PromocionesCard(promocion = promo)
                            }
                        }
                    }
                }

                // Lista de productos disponibles
                items(productos.filter { it.disponible }) { producto ->
                    ProductoCard(
                        producto = producto,
                        // Navega al detalle del producto
                        onClick = {
                            onNavigateToProducto(producto.id)
                        },
                        // Agrega el producto al carrito
                        onAddToCart = {
                            carritoViewModel.agregarProducto(producto)
                        }
                    )
                }
            }
        }
    }
}

```

screen/usuario/PedidosScreen.kt
```
package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.screen.usuario

// Imports para layouts y listas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

// Icons de Material
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

// Componentes Material 3
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

// Firebase Auth para obtener el usuario actual
import com.google.firebase.auth.FirebaseAuth

// Modelo y ViewModel
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model.Pedido
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel.PedidoViewModel

// Utilidades para manejo de fechas
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PedidosScreen(
    // Navega a la pantalla anterior
    onNavigateBack: () -> Unit,
    // ViewModel de pedidos
    pedidoViewModel: PedidoViewModel = viewModel()
) {

    // Lista de pedidos observada como estado
    val pedidos by pedidoViewModel.pedidos.collectAsState()

    // UID del usuario autenticado en Firebase
    val uid = FirebaseAuth.getInstance().currentUser?.uid

    // Efecto que carga los pedidos del usuario cuando el UID cambia
    LaunchedEffect(uid) {
        if (uid != null) {
            pedidoViewModel.cargarPedidosUsuario(uid)
        }
    }

    // Estructura principal de la pantalla
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Pedidos") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, null)
                    }
                }
            )
        }
    ) { padding ->

        // Caso: usuario no autenticado
        if (uid == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Usuario no autenticado")
            }

        // Caso: usuario autenticado pero sin pedidos
        } else if (pedidos.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No tienes pedidos")
            }

        // Caso: se muestran los pedidos del usuario
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Se recorre la lista de pedidos
                items(pedidos) { pedido ->
                    PedidoCard(pedido)
                }
            }
        }
    }
}

// Composable que representa una tarjeta individual de pedido
@Composable
fun PedidoCard(pedido: Pedido) {

    // Formato para mostrar la fecha del pedido
    val dateFormat = remember {
        SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    }

    // Conversión segura de la fecha del pedido a texto
    val fechaStr = try {
        when (val f = pedido.fecha) {
            is com.google.firebase.Timestamp -> dateFormat.format(f.toDate())
            is Date -> dateFormat.format(f)
            else -> "Fecha no disponible"
        }
    } catch (e: Exception) {
        "Fecha no disponible"
    }

    // Se toma solo una parte del ID del pedido para mostrarlo
    val pedidoId = try {
        pedido.id.takeIf { it.length >= 8 }?.take(8) ?: "--------"
    } catch (e: Exception) {
        "--------"
    }

    // Texto y color según el estado del pedido
    val (estadoTexto, estadoColor) = when (pedido.estado) {
        "pendiente" -> "Pendiente" to MaterialTheme.colorScheme.tertiary
        "en_proceso" -> "En Proceso" to MaterialTheme.colorScheme.primary
        "completado" -> "Completado" to Color(0xFF4CAF50)
        "cancelado" -> "Cancelado" to MaterialTheme.colorScheme.error
        else -> "Desconocido" to MaterialTheme.colorScheme.outline
    }

    // Tarjeta que muestra la información del pedido
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Encabezado del pedido (ID y estado)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Pedido #$pedidoId",
                    style = MaterialTheme.typography.titleMedium
                )

                // Etiqueta del estado del pedido
                Surface(
                    color = estadoColor.copy(alpha = 0.15f),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        estadoTexto,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        color = estadoColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Fecha del pedido
            Text(fechaStr, style = MaterialTheme.typography.bodySmall)

            Spacer(modifier = Modifier.height(12.dp))

            // Lista de productos del pedido
            if (pedido.detalles.isNullOrEmpty()) {
                Text("Sin productos")
            } else {
                pedido.detalles.forEach { detalle ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("${detalle.cantidad}x ${detalle.nombreProducto}")
                        Text("$${"%.2f".format(detalle.subtotal)}")
                    }
                }
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // Total del pedido
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Total", fontWeight = FontWeight.Bold)
                Text(
                    "$${"%.2f".format(pedido.total)}",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

```

screen/usuario/PerfilScreen.kt
```
package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.screen.usuario

// Imports para layouts y componentes de Material
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

// ViewModel de autenticación
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel.AuthViewModel

// Se habilita Material3 experimental para usar TopAppBar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    // Navega a la pantalla anterior
    onNavigateBack: () -> Unit,
    // Cierra la sesión del usuario
    onLogout: () -> Unit,
    // ViewModel de autenticación
    authViewModel: AuthViewModel = viewModel()
) {

    // Usuario actualmente autenticado
    val currentUser by authViewModel.currentUser.collectAsState()

    // Estructura principal de la pantalla
    Scaffold(
        // Barra superior
        topBar = {
            TopAppBar(
                title = { Text("Mi Perfil") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->

        // Contenedor principal en columna
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            // Tarjeta con información principal del usuario
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // Ícono del perfil
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Perfil",
                        modifier = Modifier.size(80.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Nombre del usuario
                    Text(
                        text = currentUser?.nombre ?: "Usuario",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    // Correo del usuario
                    Text(
                        text = currentUser?.correo ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tarjeta con información adicional del perfil
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {

                    // Fila con el teléfono del usuario
                    ProfileInfoRow(
                        icon = Icons.Default.Phone,
                        label = "Teléfono",
                        value = currentUser?.telefono ?: "-"
                    )

                    Divider(modifier = Modifier.padding(vertical = 12.dp))

                    // Fila con el rol del usuario
                    ProfileInfoRow(
                        icon = Icons.Default.AdminPanelSettings,
                        label = "Rol",
                        value = currentUser?.rol ?: "Usuario"
                    )
                }
            }

            // Espacio flexible para empujar el botón hacia abajo
            Spacer(modifier = Modifier.weight(1f))

            // Botón para cerrar sesión
            Button(
                onClick = onLogout,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Icon(Icons.Default.ExitToApp, "Cerrar Sesión")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Cerrar Sesión")
            }
        }
    }
}

// Composable reutilizable para mostrar una fila de información del perfil
@Composable
fun ProfileInfoRow(
    // Ícono que representa el tipo de información
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    // Etiqueta del dato (ej. Teléfono, Rol)
    label: String,
    // Valor del dato
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Ícono del dato
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Texto descriptivo del dato
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

```

screen/usuario/ProductoDetalleScreen.kt
```
package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.screen.usuario

// Imports para layouts, íconos y Material 3
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

// ViewModels usados
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel.CarritoViewModel
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel.ProductoViewModel

// Se habilita Material3 experimental para usar TopAppBar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoDetalleScreen(
    // ID del producto seleccionado
    productoId: String,
    // Navega a la pantalla anterior
    onNavigateBack: () -> Unit,
    // ViewModel de productos
    productoViewModel: ProductoViewModel = viewModel(),
    // ViewModel del carrito (se recibe por parámetro)
    carritoViewModel: CarritoViewModel
) {

    // Cantidad seleccionada por el usuario
    var cantidad by remember { mutableStateOf(1) }

    // Estado para mostrar el mensaje de producto agregado
    var productoAgregado by remember { mutableStateOf(false) }

    // Efecto que se ejecuta cuando cambia el ID del producto
    LaunchedEffect(productoId) {
        // Aquí se podrían cargar detalles específicos del producto si fuera necesario
    }

    // Lista de productos observada como estado
    val productos by productoViewModel.productos.collectAsState()

    // Se busca el producto seleccionado por su ID
    val producto = productos.find { it.id == productoId }

    // Estructura principal de la pantalla
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Producto") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->

        // Solo se muestra el contenido si el producto existe
        if (producto != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {

                // Imagen del producto (comentada por ahora)
                /*
                AsyncImage(
                    model = producto.imagenUrl.ifEmpty { "https://via.placeholder.com/400" },
                    contentDescription = producto.nombre,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentScale = ContentScale.Crop
                )
                */

                Spacer(modifier = Modifier.height(16.dp))

                // Nombre del producto
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Precio del producto
                Text(
                    text = "$${producto.precio}",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Descripción del producto
                Text(
                    text = producto.descripcion,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Selector de cantidad
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Cantidad:",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Botón para disminuir la cantidad
                        IconButton(onClick = { if (cantidad > 1) cantidad-- }) {
                            Icon(Icons.Default.Remove, "Disminuir")
                        }

                        // Cantidad seleccionada
                        Text(
                            text = cantidad.toString(),
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )

                        // Botón para aumentar la cantidad
                        IconButton(onClick = { cantidad++ }) {
                            Icon(Icons.Default.Add, "Aumentar")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Botón para agregar el producto al carrito
                Button(
                    onClick = {
                        // Se agrega el producto al carrito según la cantidad seleccionada
                        repeat(cantidad) {
                            carritoViewModel.agregarProducto(producto)
                        }
                        productoAgregado = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text("Agregar al Carrito - $${producto.precio * cantidad}")
                }

                // Mensaje de confirmación cuando el producto se agrega
                if (productoAgregado) {
                    Snackbar(
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text("Producto agregado al carrito")
                    }
                }
            }
        }
    }
}

```

# Pasamos a los temas y colores de la app
theme/Color.kt
```
// Paquete donde se definen los colores del tema de la aplicación
package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.theme

// Import necesario para usar colores en Jetpack Compose
import androidx.compose.ui.graphics.Color

// Colores base generados por el template de Material (modo claro/oscuro)
val Purple80 = Color(0xFFD0BCFF)        // Morado claro (modo oscuro)
val PurpleGrey80 = Color(0xFFCCC2DC)   // Gris morado claro
val Pink80 = Color(0xFFEFB8C8)         // Rosa claro

val Purple40 = Color(0xFF6650a4)       // Morado principal (modo claro)
val PurpleGrey40 = Color(0xFF625b71)   // Gris morado oscuro
val Pink40 = Color(0xFF7D5260)         // Rosa oscuro

// -----------------------------
// Colores personalizados para la cafetería
// -----------------------------

val CoffeeLight = Color(0xFF8B4513)     // Café claro, ideal para botones o acentos
val CoffeeDark = Color(0xFF5D2E0F)      // Café oscuro, útil para fondos o headers
val CoffeeAccent = Color(0xFFD2691E)    // Café anaranjado, para resaltar elementos
val CreamLight = Color(0xFFFFF8DC)      // Color crema claro, fondo suave
val CreamDark = Color(0xFFFFE4B5)       // Crema oscuro, variación para contraste

```

theme/Theme.kt
```
/****************************************************
 * Paquete donde se define el tema general de la app *
 ****************************************************/
package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.theme

/****************************************************
 * Imports necesarios para el manejo del tema       *
 ****************************************************/
import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/****************************************************
 * Esquema de colores para el modo oscuro            *
 ****************************************************/
private val DarkColorScheme = darkColorScheme(
    primary = CoffeeAccent,        // Color principal
    secondary = CoffeeDark,         // Color secundario
    tertiary = Pink80,              // Color terciario
    background = Color(0xFF1C1B1F), // Fondo principal
    surface = Color(0xFF2C2B2F)     // Superficies (cards, dialogs)
)

/****************************************************
 * Esquema de colores para el modo claro             *
 ****************************************************/
private val LightColorScheme = lightColorScheme(
    primary = CoffeeLight,          // Color principal
    secondary = CoffeeAccent,        // Color secundario
    tertiary = Pink40,               // Color terciario
    background = CreamLight,         // Fondo claro
    surface = Color(0xFFFFFBFE),     // Superficie clara
    onPrimary = Color.White,         // Texto sobre primary
    onSecondary = Color.White,       // Texto sobre secondary
    onTertiary = Color.White,        // Texto sobre tertiary
    onBackground = Color(0xFF1C1B1F),// Texto sobre background
    onSurface = Color(0xFF1C1B1F)    // Texto sobre surface
)

/****************************************************
 * Tema principal de la aplicación                   *
 ****************************************************/
@Composable
fun CafeteriaUniversitariaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // Detecta modo oscuro del sistema
    dynamicColor: Boolean = false,              // Colores dinámicos (Android 12+)
    content: @Composable () -> Unit             // Contenido de la app
) {

    /************************************************
     * Selección del esquema de colores             *
     ************************************************/
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme)
                dynamicDarkColorScheme(context)
            else
                dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    /************************************************
     * Configuración de la barra de estado           *
     ************************************************/
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window

            // Cambia el color de la status bar
            window.statusBarColor = colorScheme.primary.toArgb()

            // Define el color de los íconos de la status bar
            WindowCompat
                .getInsetsController(window, view)
                .isAppearanceLightStatusBars = !darkTheme
        }
    }

    /************************************************
     * Aplicación del tema Material 3               *
     ************************************************/
    MaterialTheme(
        colorScheme = colorScheme, // Esquema de colores
        typography = Typography,   // Tipografía definida
        content = content          // Contenido de la aplicación
    )
}

```

theme/Type.kt
```
/****************************************************
 * Paquete donde se define la tipografía de la app   *
 ****************************************************/
package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.theme

/****************************************************
 * Imports necesarios para estilos de texto         *
 ****************************************************/
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/****************************************************
 * Definición de la tipografía general de la app     *
 ****************************************************/
val Typography = Typography(

    /************************************************
     * Estilo para textos largos (descripciones)    *
     ************************************************/
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,   // Fuente por defecto del sistema
        fontWeight = FontWeight.Normal,    // Grosor normal
        fontSize = 16.sp,                  // Tamaño de letra
        lineHeight = 24.sp,                // Altura de línea
        letterSpacing = 0.5.sp             // Espaciado entre letras
    ),

    /************************************************
     * Estilo para títulos principales              *
     ************************************************/
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,   // Fuente por defecto
        fontWeight = FontWeight.Bold,      // Texto en negritas
        fontSize = 22.sp,                  // Tamaño de título
        lineHeight = 28.sp,                // Altura de línea
        letterSpacing = 0.sp               // Sin espaciado extra
    ),

    /************************************************
     * Estilo para etiquetas pequeñas               *
     ************************************************/
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,   // Fuente por defecto
        fontWeight = FontWeight.Medium,    // Grosor medio
        fontSize = 11.sp,                  // Tamaño pequeño
        lineHeight = 16.sp,                // Altura de línea
        letterSpacing = 0.5.sp             // Espaciado entre letras
    )
)

```

# Ahora haremos los ViewModels 
Los viewModel sirven para almacenar y gestionar los datos de la interfaz de usuario (UI), separando la lógica de la UI de las actividades/fragmentos y sobreviviendo a cambios de configuración como la rotación de pantalla, lo que evita perder datos y reconstruir la UI innecesariamente.

viewmodel/AdminViewModel.kt
```
/****************************************************
 * ViewModel para la administración de promociones  *
 ****************************************************/
package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel

/****************************************************
 * Imports necesarios para el ViewModel             *
 ****************************************************/
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model.Promocion
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.repository.PromocionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/****************************************************
 * AdminViewModel                                   *
 * Maneja la lógica de las promociones (CRUD)       *
 ****************************************************/
class AdminViewModel : ViewModel() {

    /************************************************
     * Repositorio que se encarga del acceso a datos *
     ************************************************/
    private val repository = PromocionRepository()

    /************************************************
     * StateFlow privado para manejar promociones   *
     ************************************************/
    private val _promociones = MutableStateFlow<List<Promocion>>(emptyList())

    /************************************************
     * StateFlow público que observa la UI          *
     ************************************************/
    val promociones: StateFlow<List<Promocion>> = _promociones

    /************************************************
     * Bloque init: se ejecuta al crear el ViewModel*
     ************************************************/
    init {
        cargarPromociones()
    }

    /************************************************
     * Carga las promociones desde el repositorio   *
     ************************************************/
    private fun cargarPromociones() {
        viewModelScope.launch {
            repository.getPromociones().collect { promociones ->
                _promociones.value = promociones
            }
        }
    }

    /************************************************
     * Agrega una nueva promoción                   *
     ************************************************/
    fun agregarPromocion(promocion: Promocion) {
        viewModelScope.launch {
            repository.agregarPromocion(promocion)
        }
    }

    /************************************************
     * Actualiza una promoción existente            *
     ************************************************/
    fun actualizarPromocion(id: String, promocion: Promocion) {
        viewModelScope.launch {
            repository.actualizarPromocion(id, promocion)
        }
    }

    /************************************************
     * Elimina una promoción por su ID              *
     ************************************************/
    fun eliminarPromocion(id: String) {
        viewModelScope.launch {
            repository.eliminarPromocion(id)
        }
    }
}

```
viewmodel/AuthViewModel.kt
```
/****************************************************
 * ViewModel encargado de la autenticación de usuarios
 ****************************************************/
package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel

/****************************************************
 * Imports necesarios para ViewModel y corrutinas
 ****************************************************/
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model.Usuario
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/****************************************************
 * AuthViewModel
 * Maneja login, registro, logout y estado de sesión
 ****************************************************/
class AuthViewModel : ViewModel() {

    /************************************************
     * Repositorio de autenticación
     ************************************************/
    private val repository = AuthRepository()

    /************************************************
     * Estado de autenticación (Idle, Loading, etc.)
     ************************************************/
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    /************************************************
     * Usuario actualmente autenticado
     ************************************************/
    private val _currentUser = MutableStateFlow<Usuario?>(null)
    val currentUser: StateFlow<Usuario?> = _currentUser

    /************************************************
     * Se ejecuta al crear el ViewModel
     ************************************************/
    init {
        checkAuthStatus()
    }

    /************************************************
     * Verifica si ya hay un usuario autenticado
     ************************************************/
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

    /************************************************
     * Inicia sesión con correo y contraseña
     ************************************************/
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
                    _authState.value =
                        AuthState.Error(error.message ?: "Error desconocido")
                }
            }.onFailure { error ->
                _authState.value =
                    AuthState.Error(error.message ?: "Error al iniciar sesión")
            }
        }
    }

    /************************************************
     * Registra un nuevo usuario
     ************************************************/
    fun register(
        email: String,
        password: String,
        nombre: String,
        telefono: String,
        idUniversidad: String
    ) {
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
                    _authState.value =
                        AuthState.Authenticated(usuarioCompleto)
                }
            }.onFailure { error ->
                _authState.value =
                    AuthState.Error(error.message ?: "Error al registrarse")
            }
        }
    }

    /************************************************
     * Cierra la sesión del usuario
     ************************************************/
    fun logout() {
        repository.logout()
        _currentUser.value = null
        _authState.value = AuthState.Idle
    }
}

/****************************************************
 * Estados posibles de autenticación
 ****************************************************/
sealed class AuthState {

    /************************************************
     * Estado inicial (sin acción)
     ************************************************/
    object Idle : AuthState()

    /************************************************
     * Estado de carga
     ************************************************/
    object Loading : AuthState()

    /************************************************
     * Usuario autenticado correctamente
     ************************************************/
    data class Authenticated(val usuario: Usuario) : AuthState()

    /************************************************
     * Error durante la autenticación
     ************************************************/
    data class Error(val message: String) : AuthState()
}

```

viewmodel/CarritoViewModel.kt
```
package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model.ItemCarrito
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model.Producto

/**
 * ViewModel encargado de manejar la lógica del carrito de compras.
 * Controla los productos, cantidades y el total a pagar.
 */
class CarritoViewModel : ViewModel() {

    // StateFlow privado que almacena los productos del carrito
    private val _items = MutableStateFlow<List<ItemCarrito>>(emptyList())

    // StateFlow público para observar los productos desde la UI
    val items: StateFlow<List<ItemCarrito>> = _items

    // StateFlow privado que guarda la cantidad total de productos
    private val _cantidadItems = MutableStateFlow(0)

    // StateFlow público para mostrar la cantidad total en la UI
    val cantidadItems: StateFlow<Int> = _cantidadItems

    /**
     * Calcula el total del carrito sumando los subtotales de cada producto.
     */
    val total: Double
        get() = _items.value.sumOf { it.subtotal }

    /**
     * Agrega un producto al carrito.
     * Si el producto ya existe, aumenta su cantidad.
     */
    fun agregarProducto(producto: Producto) {
        val itemExistente = _items.value.find { it.producto.id == producto.id }

        if (itemExistente != null) {
            // Aumenta la cantidad del producto existente
            itemExistente.cantidad++

            // Se reasigna la lista para notificar cambios al StateFlow
            _items.value = _items.value.toList()
        } else {
            // Agrega un nuevo producto al carrito
            _items.value = _items.value + ItemCarrito(producto, 1)
        }

        // Actualiza la cantidad total de productos
        _cantidadItems.value = _items.value.sumOf { it.cantidad }
    }

    /**
     * Elimina un producto del carrito usando su ID.
     */
    fun eliminarProducto(productoId: String) {
        _items.value = _items.value.filter { it.producto.id != productoId }
        _cantidadItems.value = _items.value.sumOf { it.cantidad }
    }

    /**
     * Actualiza la cantidad de un producto específico.
     */
    fun actualizarCantidad(productoId: String, cantidad: Int) {
        val item = _items.value.find { it.producto.id == productoId }

        if (item != null && cantidad > 0) {
            item.cantidad = cantidad
            _items.value = _items.value.toList()
        }

        // Recalcula la cantidad total
        _cantidadItems.value = _items.value.sumOf { it.cantidad }
    }

    /**
     * Limpia completamente el carrito.
     */
    fun limpiarCarrito() {
        _items.value = emptyList()
        _cantidadItems.value = 0
    }
}

```

viewmodel/PedidoViewModel.kt
```
package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model.DetallePedido
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model.ItemCarrito
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model.Pedido
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.repository.PedidoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel encargado de manejar la lógica de los pedidos.
 * Se comunica con el repositorio para crear pedidos,
 * obtener pedidos y actualizar su estado.
 */
class PedidoViewModel : ViewModel() {

    // Repositorio que maneja el acceso a Firebase
    private val repository = PedidoRepository()

    // Lista privada de pedidos (estado mutable)
    private val _pedidos = MutableStateFlow<List<Pedido>>(emptyList())

    // Exposición pública de la lista de pedidos
    val pedidos: StateFlow<List<Pedido>> = _pedidos

    // Indica si un pedido fue creado correctamente
    private val _pedidoCreado = MutableStateFlow(false)

    // Exposición pública del estado de creación del pedido
    val pedidoCreado: StateFlow<Boolean> = _pedidoCreado

    /**
     * Carga los pedidos de un usuario específico.
     */
    fun cargarPedidosUsuario(userId: String) {
        viewModelScope.launch {
            repository.getPedidosUsuario(userId).collect { pedidos ->
                _pedidos.value = pedidos
            }
        }
    }

    /**
     * Carga todos los pedidos (uso administrativo).
     */
    fun cargarTodosPedidos() {
        viewModelScope.launch {
            repository.getTodosPedidos().collect { pedidos ->
                _pedidos.value = pedidos
            }
        }
    }

    /**
     * Crea un nuevo pedido a partir de los productos del carrito.
     */
    fun crearPedido(userId: String, items: List<ItemCarrito>) {
        viewModelScope.launch {

            // Convierte los items del carrito en detalles del pedido
            val detalles = items.map { item ->
                DetallePedido(
                    id_producto = item.producto.id,
                    nombreProducto = item.producto.nombre,
                    cantidad = item.cantidad,
                    precio_unitario = item.producto.precio,
                    subtotal = item.subtotal
                )
            }

            // Construye el objeto Pedido
            val pedido = Pedido(
                id_usuario = userId,
                fecha = Timestamp.now(),
                total = items.sumOf { it.subtotal },
                estado = "pendiente",
                detalles = detalles
            )

            // Envía el pedido al repositorio
            val result = repository.crearPedido(pedido)

            // Si se crea correctamente, se notifica a la UI
            result.onSuccess {
                _pedidoCreado.value = true
            }
        }
    }

    /**
     * Actualiza el estado de un pedido (pendiente, preparado, entregado, etc.).
     */
    fun actualizarEstado(pedidoId: String, nuevoEstado: String) {
        viewModelScope.launch {
            repository.actualizarEstadoPedido(pedidoId, nuevoEstado)
        }
    }

    /**
     * Reinicia el estado del pedido creado.
     * Se usa después de mostrar un mensaje de éxito.
     */
    fun resetPedidoCreado() {
        _pedidoCreado.value = false
    }
}

```

viewmodel/ProductoViewModel.kt
```
package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model.Producto
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.repository.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.net.Uri

/**
 * ViewModel encargado de manejar la lógica de los productos.
 * Obtiene, agrega, actualiza y elimina productos desde el repositorio.
 */
class ProductoViewModel : ViewModel() {

    // Repositorio que maneja los datos de productos (Firebase)
    private val repository = ProductoRepository()

    // Lista privada de productos (estado mutable)
    private val _productos = MutableStateFlow<List<Producto>>(emptyList())

    // Exposición pública de la lista de productos para la UI
    val productos: StateFlow<List<Producto>> = _productos

    // Indica si los productos se están cargando
    private val _isLoading = MutableStateFlow(true)

    // Exposición pública del estado de carga
    val isLoading: StateFlow<Boolean> = _isLoading

    // Se ejecuta al crear el ViewModel
    init {
        cargarProductos()
    }

    /**
     * Carga la lista de productos desde el repositorio.
     */
    private fun cargarProductos() {
        viewModelScope.launch {
            _isLoading.value = true

            repository.getProductos().collect { productos ->
                _productos.value = productos
                _isLoading.value = false
            }
        }
    }

    /**
     * Agrega un nuevo producto.
     * Puede incluir una imagen opcional.
     */
    fun agregarProducto(producto: Producto, imagenUri: Uri?) {
        viewModelScope.launch {
            repository.agregarProducto(producto, imagenUri)
        }
    }

    /**
     * Actualiza un producto existente usando su ID.
     */
    fun actualizarProducto(id: String, producto: Producto) {
        viewModelScope.launch {
            repository.actualizarProducto(id, producto)
        }
    }

    /**
     * Elimina un producto usando su ID.
     */
    fun eliminarProducto(id: String) {
        viewModelScope.launch {
            repository.eliminarProducto(id)
        }
    }
}

```

# Finalmente con MainActivity, desde donde se ejecutará y unirá cada complemento
MainActivity
```
package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model.Producto
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.repository.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.net.Uri

/**
 * ViewModel que maneja la lógica de los productos.
 * Se encarga de obtener, agregar, actualizar y eliminar productos.
 */
class ProductoViewModel : ViewModel() {

    // Repositorio que conecta con la fuente de datos (Firebase)
    private val repository = ProductoRepository()

    // Lista privada de productos (estado mutable)
    private val _productos = MutableStateFlow<List<Producto>>(emptyList())

    // Lista pública observable desde la UI
    val productos: StateFlow<List<Producto>> = _productos

    // Indica si los productos se están cargando
    private val _isLoading = MutableStateFlow(true)

    // Estado de carga expuesto a la UI
    val isLoading: StateFlow<Boolean> = _isLoading

    // Se ejecuta cuando se crea el ViewModel
    init {
        cargarProductos()
    }

    /**
     * Obtiene los productos desde el repositorio.
     */
    private fun cargarProductos() {
        viewModelScope.launch {
            _isLoading.value = true

            repository.getProductos().collect { productos ->
                _productos.value = productos
                _isLoading.value = false
            }
        }
    }

    /**
     * Agrega un nuevo producto.
     * La imagen es opcional.
     */
    fun agregarProducto(producto: Producto, imagenUri: Uri?) {
        viewModelScope.launch {
            repository.agregarProducto(producto, imagenUri)
        }
    }

    /**
     * Actualiza un producto existente usando su ID.
     */
    fun actualizarProducto(id: String, producto: Producto) {
        viewModelScope.launch {
            repository.actualizarProducto(id, producto)
        }
    }

    /**
     * Elimina un producto usando su ID.
     */
    fun eliminarProducto(id: String) {
        viewModelScope.launch {
            repository.eliminarProducto(id)
        }
    }
}

```

# EJECUCIÓN
1. Abre el proyecto en Android Studio.
2. Realiza la sincronización del Gradle.
3. Ejecuta la app en un emulador o en un dispositivo Android físico

# EJEMPLOS
<img width="483" height="1079" alt="Captura de pantalla 2025-12-14 235837" src="https://github.com/user-attachments/assets/87d83429-f5e0-46ef-ad42-bc9c535038d0" />
<img width="497" height="895" alt="Captura de pantalla 2025-12-14 235717" src="https://github.com/user-attachments/assets/5bb450ec-dc35-448e-9cb9-2ca3d95be9f1" />
<img width="500" height="895" alt="Captura de pantalla 2025-12-14 235732" src="https://github.com/user-attachments/assets/bfcd6cc5-5060-4ce9-bd49-30b04be4cd41" />
<img width="481" height="1079" alt="Captura de pantalla 2025-12-14 235449" src="https://github.com/user-attachments/assets/2cb9fa0b-fe8c-4608-9c92-cb762acfc064" />
<img width="473" height="1071" alt="Captura de pantalla 2025-12-14 235701" src="https://github.com/user-attachments/assets/0dd2c43d-9ef4-42ae-908b-acf108a60457" />
<img width="492" height="1076" alt="Captura de pantalla 2025-12-15 000049" src="https://github.com/user-attachments/assets/0e323494-cfcc-458f-82da-0c61ada59b15" />


# Contactos y Agradecimietos
Ricardo Aarón Olalde Tovar - rick-20 y 
Angel Gabriel Rojas Hernandez - Deibo60
