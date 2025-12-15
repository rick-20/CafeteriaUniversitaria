package mx.edu.utng.cafeteria.cafeteriauniversitaria.ui.screen.usuario

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
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
    carritoViewModel: CarritoViewModel
) {
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
                            Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito")
                        }
                    }

                    IconButton(onClick = onNavigateToPedidos) {
                        Icon(Icons.Default.ListAlt, contentDescription = "Pedidos")
                    }

                    IconButton(onClick = onNavigateToPerfil) {
                        Icon(Icons.Default.Person, contentDescription = "Perfil")
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
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(productos.filter { it.disponible }) { producto ->
                    ProductoCard(
                        producto = producto,
                        onClick = { onNavigateToProducto(producto.id) },
                        onAddToCart = { carritoViewModel.agregarProducto(producto) }
                    )
                }
            }
        }
    }
}
