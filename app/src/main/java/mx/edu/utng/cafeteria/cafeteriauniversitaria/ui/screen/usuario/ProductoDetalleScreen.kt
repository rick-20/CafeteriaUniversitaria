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
