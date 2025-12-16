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

    AndroidX / Compose (compatibles con SDK 36)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.firebase.storage.ktx)

    Tests
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    Debug
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // --- Jetpack Compose ---
    implementation(platform("androidx.compose:compose-bom:2024.10.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.activity:activity-compose:1.9.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")

    // --- Navegación ---
    implementation("androidx.navigation:navigation-compose:2.8.2")

    // --- Retrofit para llamadas HTTP ---
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // --- Corrutinas ---
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

    // --- Serialización JSON ---
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")

    // --- Core Android ---
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")

    //Iconos
    implementation("androidx.compose.material:material-icons-extended:1.3.1")
    testImplementation(kotlin("test"))
}
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
package mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model

data class DetallePedido(
    val id_producto: String = "",
    val nombreProducto: String = "",
    val cantidad: Int = 0,
    val precio_unitario: Double = 0.0,
    val subtotal: Double = 0.0
)

El detalle pedido nos sirve para declarar los atributos del mismo, los cuales usaremos para almacenar los datos correspondientes.

data/model/ItemCarrito.kt
package mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model

data class ItemCarrito(
    val producto: Producto,
    var cantidad: Int
) {
    val subtotal: Double
        get() = producto.precio * cantidad
}

data/model/Pedido.kt
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

data/model/Producto.kt
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

data/model/Promocion.kt
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

data/model/Usuario.kt
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

  # data/repository
Este paquete se utilizara para crear los repositorios, estos nos servirán para organizar y obtener el acceso a los datos de la app, facilitando la prueba y la moduladidad.

data/repository/AuthRepository.kt

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

data/repository/PedidoRepository.kt
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

data/repository/ProductoRepository.kt
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

data/repository/PromocionRepository.kt
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

# Capa de Interfaz de Usuario (UI)
# ui/components
ui/components/ProductoCard.kt
package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model.Producto

@Composable
fun ProductoCard(
    producto: Producto,
    onClick: () -> Unit,
    onAddToCart: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$${producto.precio}",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )

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

ui/components/PromocionesCard.kt
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

@Composable
fun PromocionesCard(promocion: Promocion) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = promocion.nombre,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = promocion.descripcion,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

# ui/navegation
Pasamos al apartado de UI con navegación

ui/navegation/NavGraph.kt
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

@Composable
fun NavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel = viewModel()
) {
    val authState by authViewModel.authState.collectAsState()
    val currentUser by authViewModel.currentUser.collectAsState()

    val startDestination = when (authState) {
        is AuthState.Authenticated -> {
            if (currentUser?.rol == "admin") Screen.AdminHome.route else Screen.Home.route
        }
        else -> Screen.Login.route
    }

    val carritoViewModel: CarritoViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Auth Screens
        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                onLoginSuccess = { usuario ->
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
                onNavigateToLogin = { navController.popBackStack() },
                onRegisterSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                }
            )
        }

        // Usuario Screens
        composable(Screen.Home.route) {
            HomeScreen(
                carritoViewModel = carritoViewModel,
                onNavigateToProducto = { productoId ->
                    navController.navigate(Screen.ProductoDetalle.createRoute(productoId))
                },
                onNavigateToCarrito = { navController.navigate(Screen.Carrito.route) },
                onNavigateToPedidos = { navController.navigate(Screen.Pedidos.route) },
                onNavigateToPerfil = { navController.navigate(Screen.Perfil.route) }
            )
        }

        composable(
            route = Screen.ProductoDetalle.route,
            arguments = listOf(navArgument("productoId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productoId = backStackEntry.arguments?.getString("productoId") ?: ""
            ProductoDetalleScreen(
                productoId = productoId,
                carritoViewModel = carritoViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Carrito.route) {
            CarritoScreen(
                carritoViewModel = carritoViewModel,
                onNavigateBack = { navController.popBackStack() },
                onFinalizarCompra = { navController.navigate(Screen.Pedidos.route) }
            )
        }

        composable(Screen.Pedidos.route) {
            PedidosScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Perfil.route) {
            PerfilScreen(
                onNavigateBack = { navController.popBackStack() },
                onLogout = {
                    authViewModel.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // Admin Screens
        composable(Screen.AdminHome.route) {
            AdminHomeScreen(
                onNavigateToProductos = { navController.navigate(Screen.ProductosCrud.route) },
                onNavigateToPromociones = { navController.navigate(Screen.PromocionesAdmin.route) },
                onNavigateToPedidos = { navController.navigate(Screen.PedidosAdmin.route) },
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
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.PromocionesAdmin.route) {
            PromocionesScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.PedidosAdmin.route) {
            PedidosAdminScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}

ui/navegation/Screen.kt
package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.navegation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object ProductoDetalle : Screen("producto/{productoId}") {
        fun createRoute(productoId: String) = "producto/$productoId"
    }
    object Carrito : Screen("carrito")
    object Pedidos : Screen("pedidos")
    object Perfil : Screen("perfil")

    // Admin
    object AdminHome : Screen("admin/home")
    object ProductosCrud : Screen("admin/productos")
    object PromocionesAdmin : Screen("admin/promociones")
    object PedidosAdmin : Screen("admin/pedidos")
}

# Pasamos a las Screen o Pantallas que serán mostradas al usuario
# screen/admin
Primero con las vistas correspondientes a los administradores

screen/admin/AdminHomeScreen.kt
Esta Screen se encargará de mostrar el "Inicio" de la vista del admin
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminHomeScreen(
    onNavigateToProductos: () -> Unit,
    onNavigateToPromociones: () -> Unit,
    onNavigateToPedidos: () -> Unit,
    onLogout: () -> Unit,
    authViewModel: AuthViewModel = viewModel()
) {
    val currentUser by authViewModel.currentUser.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Panel de Administración") },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Default.ExitToApp, "Cerrar Sesión")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Admin",
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(16.dp))
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

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    AdminMenuCard(
                        icon = Icons.Default.Fastfood,
                        title = "Productos",
                        description = "Gestionar menú",
                        onClick = onNavigateToProductos
                    )
                }
                item {
                    AdminMenuCard(
                        icon = Icons.Default.LocalOffer,
                        title = "Promociones",
                        description = "Gestionar ofertas",
                        onClick = onNavigateToPromociones
                    )
                }
                item {
                    AdminMenuCard(
                        icon = Icons.Default.Receipt,
                        title = "Pedidos",
                        description = "Ver todos los pedidos",
                        onClick = onNavigateToPedidos
                    )
                }
                /*item {
                    AdminMenuCard(
                        icon = Icons.Default.BarChart,
                        title = "Estadísticas",
                        description = "Próximamente",
                        onClick = { },
                        enabled = false
                    )
                }*/
            }
        }
    }
}

@Composable
fun AdminMenuCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        enabled = enabled
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(48.dp),
                tint = if (enabled) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = if (enabled) MaterialTheme.colorScheme.onSurface
                else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

screen/admin/PedidosAdminScreen.kt
Vista de los pedidos de admin, es decir, aquí le aparecerán los pedidos de los alumnos
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PedidosAdminScreen(
    onNavigateBack: () -> Unit,
    pedidoViewModel: PedidoViewModel = viewModel()
) {
    val pedidos by pedidoViewModel.pedidos.collectAsState()

    LaunchedEffect(Unit) {
        pedidoViewModel.cargarTodosPedidos()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Todos los Pedidos") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
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
                            pedidoViewModel.actualizarEstado(pedido.id, nuevoEstado)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun PedidoAdminCard(
    pedido: Pedido,
    onUpdateEstado: (String) -> Unit
) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val fechaStr = dateFormat.format(pedido.fecha.toDate())

    var expanded by remember { mutableStateOf(false) }
    var showEstadoDialog by remember { mutableStateOf(false) }

    val estadoColor = when (pedido.estado) {
        "pendiente" -> MaterialTheme.colorScheme.tertiary
        "en_proceso" -> MaterialTheme.colorScheme.primary
        "completado" -> Color(0xFF4CAF50)
        else -> MaterialTheme.colorScheme.error
    }

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
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

                Surface(
                    color = estadoColor.copy(alpha = 0.2f),
                    shape = MaterialTheme.shapes.small,
                    onClick = { showEstadoDialog = true }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = pedido.estado.replace("_", " ").capitalize(Locale.ROOT),
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

            TextButton(
                onClick = { expanded = !expanded },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(if (expanded) "Ver menos" else "Ver detalles")
                Icon(
                    if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null
                )
            }

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

screen/admin/ProductosCrudScreen.kt
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductosCrudScreen(
    onNavigateBack: () -> Unit,
    productoViewModel: ProductoViewModel = viewModel()
) {
    val productos by productoViewModel.productos.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var selectedProducto by remember { mutableStateOf<Producto?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestionar Productos") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    selectedProducto = null
                    showDialog = true
                }
            ) {
                Icon(Icons.Default.Add, "Agregar Producto")
            }
        }
    ) { paddingValues ->
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
                    onEdit = {
                        selectedProducto = producto
                        showDialog = true
                    },
                    onDelete = {
                        productoViewModel.eliminarProducto(producto.id)
                    }
                )
            }
        }
    }

    if (showDialog) {
        ProductoDialog(
            producto = selectedProducto,
            onDismiss = { showDialog = false },
            onConfirm = { producto ->
                if (selectedProducto != null) {
                    productoViewModel.actualizarProducto(selectedProducto!!.id, producto)
                } else {
                    productoViewModel.agregarProducto(producto, null)
                }
                showDialog = false
            }
        )
    }
}

@Composable
fun ProductoAdminCard(
    producto: Producto,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
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
                    text = if (producto.disponible) "Disponible" else "No disponible",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (producto.disponible) Color(0xFF4CAF50)
                    else MaterialTheme.colorScheme.error
                )
            }

            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, "Editar", tint = MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = { showDeleteDialog = true }) {
                    Icon(Icons.Default.Delete, "Eliminar", tint = MaterialTheme.colorScheme.error)
                }
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Eliminar Producto") },
            text = { Text("¿Estás seguro de que quieres eliminar ${producto.nombre}?") },
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

@Composable
fun ProductoDialog(
    producto: Producto?,
    onDismiss: () -> Unit,
    onConfirm: (Producto) -> Unit
) {
    var nombre by remember { mutableStateOf(producto?.nombre ?: "") }
    var descripcion by remember { mutableStateOf(producto?.descripcion ?: "") }
    var precio by remember { mutableStateOf(producto?.precio?.toString() ?: "") }
    var categoria by remember { mutableStateOf(producto?.categoria ?: "") }
    var disponible by remember { mutableStateOf(producto?.disponible ?: true) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (producto != null) "Editar Producto" else "Nuevo Producto") },
        text = {
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
                    val precioDouble = precio.toDoubleOrNull() ?: 0.0
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

screen/admin/PromocionesScreen.kt
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromocionesScreen(
    onNavigateBack: () -> Unit,
    adminViewModel: AdminViewModel = viewModel()
) {
    val promociones by adminViewModel.promociones.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var selectedPromocion by remember { mutableStateOf<Promocion?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestionar Promociones") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                }
            )
        },
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
                        onEdit = {
                            selectedPromocion = promocion
                            showDialog = true
                        },
                        onDelete = {
                            adminViewModel.eliminarPromocion(promocion.id)
                        }
                    )
                }
            }
        }
    }

    if (showDialog) {
        PromocionDialog(
            promocion = selectedPromocion,
            onDismiss = { showDialog = false },
            onConfirm = { promocion ->
                if (selectedPromocion != null) {
                    adminViewModel.actualizarPromocion(selectedPromocion!!.id, promocion)
                } else {
                    adminViewModel.agregarPromocion(promocion)
                }
                showDialog = false
            }
        )
    }
}

@Composable
fun PromocionCard(
    promocion: Promocion,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
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
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
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
                Text(
                    text = "${dateFormat.format(promocion.fecha_inicio.toDate())} - ${dateFormat.format(promocion.fecha_fin.toDate())}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }

            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, "Editar", tint = MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = { showDeleteDialog = true }) {
                    Icon(Icons.Default.Delete, "Eliminar", tint = MaterialTheme.colorScheme.error)
                }
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Eliminar Promoción") },
            text = { Text("¿Estás seguro de que quieres eliminar ${promocion.nombre}?") },
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

@Composable
fun PromocionDialog(
    promocion: Promocion?,
    onDismiss: () -> Unit,
    onConfirm: (Promocion) -> Unit
) {
    var nombre by remember { mutableStateOf(promocion?.nombre ?: "") }
    var descripcion by remember { mutableStateOf(promocion?.descripcion ?: "") }
    var puntos by remember { mutableStateOf(promocion?.puntos_requeridos?.toString() ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (promocion != null) "Editar Promoción" else "Nueva Promoción") },
        text = {
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
                    val puntosInt = puntos.toIntOrNull() ?: 0
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

# screen/auth
Este paquete será la autenticación, donde se guardará el inicio, registro y cierre de sesión de los usuarios

screen/auth/LoginScreen.kt
package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.screen.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model.Usuario
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel.AuthState
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: (Usuario) -> Unit,
    authViewModel: AuthViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val authState by authViewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        if (authState is AuthState.Authenticated) {
            onLoginSuccess((authState as AuthState.Authenticated).usuario)
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

            Text(
                text = "Cafetería Universitaria",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Bienvenido de vuelta",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Email Field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                leadingIcon = { Icon(Icons.Default.Email, null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password Field
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                leadingIcon = { Icon(Icons.Default.Lock, null) },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility
                            else Icons.Default.VisibilityOff,
                            contentDescription = if (passwordVisible) "Ocultar contraseña"
                            else "Mostrar contraseña"
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Login Button
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
                if (authState is AuthState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Iniciar Sesión", style = MaterialTheme.typography.titleMedium)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Register Link
            TextButton(onClick = onNavigateToRegister) {
                Text("¿No tienes cuenta? Regístrate")
            }

            // Error Message
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

screen/auth/RegistrerScreen.kt
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel.AuthState
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onNavigateToLogin: () -> Unit,
    onRegisterSuccess: () -> Unit,
    authViewModel: AuthViewModel = viewModel()
) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val authState by authViewModel.authState.collectAsState()
    val scrollState = rememberScrollState()

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

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre completo") },
                leadingIcon = { Icon(Icons.Default.Person, null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                leadingIcon = { Icon(Icons.Default.Email, null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = telefono,
                onValueChange = { telefono = it },
                label = { Text("Teléfono") },
                leadingIcon = { Icon(Icons.Default.Phone, null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                leadingIcon = { Icon(Icons.Default.Lock, null) },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility
                            else Icons.Default.VisibilityOff,
                            contentDescription = null
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirmar contraseña") },
                leadingIcon = { Icon(Icons.Default.Lock, null) },
                trailingIcon = {
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(
                            imageVector = if (confirmPasswordVisible) Icons.Default.Visibility
                            else Icons.Default.VisibilityOff,
                            contentDescription = null
                        )
                    }
                },
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = confirmPassword.isNotEmpty() && password != confirmPassword
            )

            if (confirmPassword.isNotEmpty() && password != confirmPassword) {
                Text(
                    text = "Las contraseñas no coinciden",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (nombre.isNotBlank() && email.isNotBlank() &&
                        telefono.isNotBlank() && password.isNotBlank() &&
                        password == confirmPassword) {
                        authViewModel.register(email, password, nombre, telefono, "")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = authState !is AuthState.Loading &&
                        nombre.isNotBlank() && email.isNotBlank() &&
                        telefono.isNotBlank() && password.isNotBlank() &&
                        password == confirmPassword
            ) {
                if (authState is AuthState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Registrarse", style = MaterialTheme.typography.titleMedium)
                }
            }

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

# Pasamos a las vistas de los usuarios (Clientes)
# screen/usuario
screen/usuario/CarritoScreen.kt
package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.screen.usuario

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
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model.ItemCarrito
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel.AuthViewModel
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel.CarritoViewModel
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel.PedidoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(
    onNavigateBack: () -> Unit,
    onFinalizarCompra: () -> Unit,
    carritoViewModel: CarritoViewModel,
    pedidoViewModel: PedidoViewModel = viewModel(),
    authViewModel: AuthViewModel = viewModel()
) {
    val items by carritoViewModel.items.collectAsState()
    val total = carritoViewModel.total
    val pedidoCreado by pedidoViewModel.pedidoCreado.collectAsState()
    val currentUser by authViewModel.currentUser.collectAsState()

    LaunchedEffect(pedidoCreado) {
        if (pedidoCreado) {
            carritoViewModel.limpiarCarrito()
            pedidoViewModel.resetPedidoCreado()
            onFinalizarCompra()
        }
    }

    Scaffold(
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
        bottomBar = {
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

                        Button(
                            onClick = {
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(items) { item ->
                    CarritoItemCard(
                        item = item,
                        onUpdateCantidad = { cantidad ->
                            carritoViewModel.actualizarCantidad(item.producto.id, cantidad)
                        },
                        onEliminar = {
                            carritoViewModel.eliminarProducto(item.producto.id)
                        }
                    )
                }
            }
        }
    }
}

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

screen/usuario/HomeScreen.kt
package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.screen.usuario

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.components.ProductoCard
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.components.PromocionesCard
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel.AdminViewModel
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel.CarritoViewModel
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel.ProductoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToProducto: (String) -> Unit,
    onNavigateToCarrito: () -> Unit,
    onNavigateToPedidos: () -> Unit,
    onNavigateToPerfil: () -> Unit,
    productoViewModel: ProductoViewModel = viewModel(),
    carritoViewModel: CarritoViewModel,
    adminViewModel: AdminViewModel = viewModel()
) {
    val promociones by adminViewModel.promociones.collectAsState()
    val productos by productoViewModel.productos.collectAsState()
    val isLoading by productoViewModel.isLoading.collectAsState()
    val cantidadCarrito by carritoViewModel.cantidadItems.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cafetería Universitaria") },
                actions = {
                    BadgedBox(
                        badge = {
                            if (cantidadCarrito > 0) {
                                Badge { Text(cantidadCarrito.toString()) }
                            }
                        }
                    ) {
                        IconButton(onClick = onNavigateToCarrito) {
                            Icon(Icons.Default.ShoppingCart, "Carrito")
                        }
                    }
                    IconButton(onClick = onNavigateToPedidos) {
                        Icon(Icons.Default.ListAlt, "Pedidos")
                    }
                    IconButton(onClick = onNavigateToPerfil) {
                        Icon(Icons.Default.Person, "Perfil")
                    }
                }
            )
        }
    ) { paddingValues ->

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

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {


                if (promociones.isNotEmpty()) {
                    item(span = { GridItemSpan(2) }) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Promociones",
                                style = MaterialTheme.typography.titleLarge
                            )

                            promociones.forEach { promo ->
                                PromocionesCard(promocion = promo)
                            }
                        }
                    }
                }

                items(productos.filter { it.disponible }) { producto ->
                    ProductoCard(
                        producto = producto,
                        onClick = { onNavigateToProducto(producto.id) },
                        onAddToCart = {
                            carritoViewModel.agregarProducto(producto)
                        }
                    )
                }
            }
        }
    }
}

screen/usuario/PedidosScreen.kt
package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.screen.usuario

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model.Pedido
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel.AuthViewModel
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel.PedidoViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PedidosScreen(
    onNavigateBack: () -> Unit,
    pedidoViewModel: PedidoViewModel = viewModel()
) {
    val pedidos by pedidoViewModel.pedidos.collectAsState()
    val uid = FirebaseAuth.getInstance().currentUser?.uid

    LaunchedEffect(uid) {
        if (uid != null) {
            pedidoViewModel.cargarPedidosUsuario(uid)
        }
    }

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

        if (uid == null) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Usuario no autenticado")
            }
        } else if (pedidos.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No tienes pedidos")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(pedidos) { pedido ->
                    PedidoCard(pedido)
                }
            }
        }
    }
}



@Composable
fun PedidoCard(pedido: Pedido) {

    val dateFormat = remember {
        SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    }

    // 🔐 Fecha ultra segura
    val fechaStr = try {
        when (val f = pedido.fecha) {
            is com.google.firebase.Timestamp -> dateFormat.format(f.toDate())
            is Date -> dateFormat.format(f)
            else -> "Fecha no disponible"
        }
    } catch (e: Exception) {
        "Fecha no disponible"
    }

    // 🔐 ID ultra seguro
    val pedidoId = try {
        pedido.id.takeIf { it.length >= 8 }?.take(8) ?: "--------"
    } catch (e: Exception) {
        "--------"
    }

    val (estadoTexto, estadoColor) = when (pedido.estado) {
        "pendiente" -> "Pendiente" to MaterialTheme.colorScheme.tertiary
        "en_proceso" -> "En Proceso" to MaterialTheme.colorScheme.primary
        "completado" -> "Completado" to Color(0xFF4CAF50)
        "cancelado" -> "Cancelado" to MaterialTheme.colorScheme.error
        else -> "Desconocido" to MaterialTheme.colorScheme.outline
    }

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Pedido #$pedidoId", style = MaterialTheme.typography.titleMedium)
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
            Text(fechaStr, style = MaterialTheme.typography.bodySmall)

            Spacer(modifier = Modifier.height(12.dp))

            // 🔐 Detalles ultra seguros
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

screen/usuario/PerfilScreen.kt
package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.screen.usuario

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    onNavigateBack: () -> Unit,
    onLogout: () -> Unit,
    authViewModel: AuthViewModel = viewModel()
) {
    val currentUser by authViewModel.currentUser.collectAsState()

    Scaffold(
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Perfil",
                        modifier = Modifier.size(80.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = currentUser?.nombre ?: "Usuario",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Text(
                        text = currentUser?.correo ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    ProfileInfoRow(
                        icon = Icons.Default.Phone,
                        label = "Teléfono",
                        value = currentUser?.telefono ?: "-"
                    )

                    Divider(modifier = Modifier.padding(vertical = 12.dp))

                    /*ProfileInfoRow(
                        icon = Icons.Default.Star,
                        label = "Puntos Acumulados",
                        value = currentUser?.puntos_acumulados?.toString() ?: "0"
                    )*/

                    Divider(modifier = Modifier.padding(vertical = 12.dp))

                    ProfileInfoRow(
                        icon = Icons.Default.AdminPanelSettings,
                        label = "Rol",
                        value = currentUser?.rol ?: "Usuario"
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

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

@Composable
fun ProfileInfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(16.dp))
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

screen/usuario/ProductoDetalleScreen.kt
package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.screen.usuario

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
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel.CarritoViewModel
import mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel.ProductoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoDetalleScreen(
    productoId: String,
    onNavigateBack: () -> Unit,
    productoViewModel: ProductoViewModel = viewModel(),
    carritoViewModel: CarritoViewModel
) {
    var cantidad by remember { mutableStateOf(1) }
    var productoAgregado by remember { mutableStateOf(false) }

    LaunchedEffect(productoId) {
        // Aquí podrías cargar detalles específicos del producto si es necesario
    }

    val productos by productoViewModel.productos.collectAsState()
    val producto = productos.find { it.id == productoId }

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
        if (producto != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                /*AsyncImage(
                    model = producto.imagenUrl.ifEmpty { "https://via.placeholder.com/400" },
                    contentDescription = producto.nombre,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentScale = ContentScale.Crop
                )*/

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "$${producto.precio}",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = producto.descripcion,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cantidad:", style = MaterialTheme.typography.titleMedium)

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { if (cantidad > 1) cantidad-- }) {
                            Icon(Icons.Default.Remove, "Disminuir")
                        }
                        Text(
                            text = cantidad.toString(),
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        IconButton(onClick = { cantidad++ }) {
                            Icon(Icons.Default.Add, "Aumentar")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
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

# Pasamos a los temas y colores de la app
theme/Color.kt
package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)
// Colores personalizados para cafetería
val CoffeeLight = Color(0xFF8B4513)
val CoffeeDark = Color(0xFF5D2E0F)
val CoffeeAccent = Color(0xFFD2691E)
val CreamLight = Color(0xFFFFF8DC)
val CreamDark = Color(0xFFFFE4B5)

theme/Theme.kt
package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.theme

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

private val DarkColorScheme = darkColorScheme(
    primary = CoffeeAccent,
    secondary = CoffeeDark,
    tertiary = Pink80,
    background = Color(0xFF1C1B1F),
    surface = Color(0xFF2C2B2F)
)

private val LightColorScheme = lightColorScheme(
    primary = CoffeeLight,
    secondary = CoffeeAccent,
    tertiary = Pink40,
    background = CreamLight,
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F)
)

@Composable
fun CafeteriaUniversitariaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

theme/Type.kt
package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)

# Ahora haremos los ViewModels 
Los viewModel sirven para almacenar y gestionar los datos de la interfaz de usuario (UI), separando la lógica de la UI de las actividades/fragmentos y sobreviviendo a cambios de configuración como la rotación de pantalla, lo que evita perder datos y reconstruir la UI innecesariamente.

viewmodel/AdminViewModel.kt
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

viewmodel/AuthViewModel.kt
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

viewmodel/CarritoViewModel.kt
package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model.ItemCarrito
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model.Producto

class CarritoViewModel : ViewModel() {

    private val _items = MutableStateFlow<List<ItemCarrito>>(emptyList())
    val items: StateFlow<List<ItemCarrito>> = _items

    private val _cantidadItems = MutableStateFlow(0)
    val cantidadItems: StateFlow<Int> = _cantidadItems

    val total: Double
        get() = _items.value.sumOf { it.subtotal }

    fun agregarProducto(producto: Producto) {
        //val itemExistente = _items.value.toMutableList()

        val itemExistente = _items.value.find { it.producto.id == producto.id }

        if (itemExistente != null) {
            itemExistente.cantidad++
            _items.value = _items.value.toList()
        } else {
            _items.value = _items.value + ItemCarrito(producto, 1)
        }

        _cantidadItems.value = _items.value.sumOf { it.cantidad }
    }

    fun eliminarProducto(productoId: String) {
        _items.value = _items.value.filter { it.producto.id != productoId }
        _cantidadItems.value = _items.value.sumOf { it.cantidad }
    }

    fun actualizarCantidad(productoId: String, cantidad: Int) {
        val item = _items.value.find { it.producto.id == productoId }
        if (item != null && cantidad > 0) {
            item.cantidad = cantidad
            _items.value = _items.value.toList()
        }
        _cantidadItems.value = _items.value.sumOf { it.cantidad }
    }

    fun limpiarCarrito() {
        _items.value = emptyList()
        _cantidadItems.value = 0
    }
}

viewmodel/PedidoViewModel.kt
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

class PedidoViewModel : ViewModel() {
    private val repository = PedidoRepository()

    private val _pedidos = MutableStateFlow<List<Pedido>>(emptyList())
    val pedidos: StateFlow<List<Pedido>> = _pedidos

    private val _pedidoCreado = MutableStateFlow<Boolean>(false)
    val pedidoCreado: StateFlow<Boolean> = _pedidoCreado

    fun cargarPedidosUsuario(userId: String) {
        viewModelScope.launch {
            repository.getPedidosUsuario(userId).collect { pedidos ->
                _pedidos.value = pedidos
            }
        }
    }

    fun cargarTodosPedidos() {
        viewModelScope.launch {
            repository.getTodosPedidos().collect { pedidos ->
                _pedidos.value = pedidos
            }
        }
    }

    fun crearPedido(userId: String, items: List<ItemCarrito>) {
        viewModelScope.launch {
            val detalles = items.map { item ->
                DetallePedido(
                    id_producto = item.producto.id,
                    nombreProducto = item.producto.nombre,
                    cantidad = item.cantidad,
                    precio_unitario = item.producto.precio,
                    subtotal = item.subtotal
                )
            }

            val pedido = Pedido(
                id_usuario = userId,
                fecha = Timestamp.now(),
                total = items.sumOf { it.subtotal },
                estado = "pendiente",
                detalles = detalles
            )

            val result = repository.crearPedido(pedido)
            result.onSuccess {
                _pedidoCreado.value = true
            }
        }
    }

    fun actualizarEstado(pedidoId: String, nuevoEstado: String) {
        viewModelScope.launch {
            repository.actualizarEstadoPedido(pedidoId, nuevoEstado)
        }
    }

    fun resetPedidoCreado() {
        _pedidoCreado.value = false
    }
}

viewmodel/ProductoViewModel.kt
package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model.Producto
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.repository.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.net.Uri

class ProductoViewModel : ViewModel() {
    private val repository = ProductoRepository()

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        cargarProductos()
    }

    private fun cargarProductos() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getProductos().collect { productos ->
                _productos.value = productos
                _isLoading.value = false
            }
        }
    }

    fun agregarProducto(producto: Producto, imagenUri: Uri?) {
        viewModelScope.launch {
            repository.agregarProducto(producto, imagenUri)
        }
    }

    fun actualizarProducto(id: String, producto: Producto) {
        viewModelScope.launch {
            repository.actualizarProducto(id, producto)
        }
    }

    fun eliminarProducto(id: String) {
        viewModelScope.launch {
            repository.eliminarProducto(id)
        }
    }
}

# Finalmente con MainActivity, desde donde se ejecutará y unirá cada complemento
MainActivity
package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.model.Producto
import mx.edu.utng.cafeteria.cafeteriauniversitaria.data.repository.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.net.Uri

class ProductoViewModel : ViewModel() {
    private val repository = ProductoRepository()

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        cargarProductos()
    }

    private fun cargarProductos() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getProductos().collect { productos ->
                _productos.value = productos
                _isLoading.value = false
            }
        }
    }

    fun agregarProducto(producto: Producto, imagenUri: Uri?) {
        viewModelScope.launch {
            repository.agregarProducto(producto, imagenUri)
        }
    }

    fun actualizarProducto(id: String, producto: Producto) {
        viewModelScope.launch {
            repository.actualizarProducto(id, producto)
        }
    }

    fun eliminarProducto(id: String) {
        viewModelScope.launch {
            repository.eliminarProducto(id)
        }
    }
}

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
