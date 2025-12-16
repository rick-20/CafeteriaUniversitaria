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
