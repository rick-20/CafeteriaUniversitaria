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
