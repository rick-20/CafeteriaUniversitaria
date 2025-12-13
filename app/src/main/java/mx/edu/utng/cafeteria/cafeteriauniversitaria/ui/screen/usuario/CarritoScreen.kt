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
    carritoViewModel: CarritoViewModel = viewModel(),
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