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
