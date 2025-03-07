package fr.isen.ngnepiepaye.isensmartcompanion

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Event
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import com.google.gson.Gson



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsScreen(
    navController: NavController,
    viewModel: EventViewModel = viewModel()
) {
    val events by viewModel.events.observeAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        viewModel.fetchEvent()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Événements") })
        }
    ) { padding ->
        if (events.isEmpty()) {
            println("⚠️ Aucun événement trouvé dans EventsScreen.kt")
            Text(
                text = "Aucun événement disponible.",
                modifier = Modifier.padding(16.dp),
                color = Color.Red
            )
        } else {
            println("✅ ${events.size} événements affichés.")
            LazyColumn(contentPadding = padding) {
                items(events) { event ->
                    EventItem(event = event) {
                        // ✅ Convertir `event` en JSON et l'encoder pour la navigation
                        val eventJson = URLEncoder.encode(Gson().toJson(event), StandardCharsets.UTF_8.toString())
                        navController.navigate("event_details/$eventJson")
                    }
                }
            }
        }
    }
}

@Composable
fun EventItem(event: Event, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row (verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Event, // 📅 Icône événement
                    contentDescription = "Événement",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = event.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.AccessTime, // ⏰ Icône date/heure
                    contentDescription = "Date de l'événement",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = event.date,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}
