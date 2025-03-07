package fr.isen.ngnepiepaye.isensmartcompanion

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.gson.Gson
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(navController: NavController, eventJson: String?) {
    val context = LocalContext.current
    val event = remember {
        Gson().fromJson(
            URLDecoder.decode(eventJson, StandardCharsets.UTF_8.toString()),
            Event::class.java
        )
    }

    val sharedPreferences = context.getSharedPreferences("event_prefs", Context.MODE_PRIVATE)
    val isNotificationEnabled = remember { mutableStateOf(sharedPreferences.getBoolean(event.id ?: "", false)) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Détails de l'événement") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Retour")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        val newValue = !isNotificationEnabled.value
                        isNotificationEnabled.value = newValue

                        sharedPreferences.edit().putBoolean(event.id ?: "", newValue).apply()

                        if (newValue) {
                            scheduleNotification(context, event.title, 10)
                        }
                    }) {
                        Icon(
                            imageVector = if (isNotificationEnabled.value) Icons.Default.Notifications else Icons.Default.NotificationsOff,
                            contentDescription = "Activer/Désactiver la notification"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(text = event.title, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "📅 Date: ${event.date}", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "📍 Lieu: ${event.location ?: "Non spécifié"}", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "📌 Catégorie: ${event.category ?: "Non spécifiée"}", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "ℹ️ Description:", style = MaterialTheme.typography.bodyLarge)
            Text(text = event.description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}