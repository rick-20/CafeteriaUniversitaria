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