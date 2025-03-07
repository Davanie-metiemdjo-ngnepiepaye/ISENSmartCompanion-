package fr.isen.ngnepiepaye.isensmartcompanion

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.getValue

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.NavHostController




@Composable
fun MainActivityScreeen(navController: NavHostController) { // ✅ Correction ici
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "main"

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (!currentRoute.startsWith("event_details/")) {
                NavigationBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                ) {
                    NavigationBarItem(
                        selected = currentRoute == "main",
                        onClick = { navController.navigate("main") },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Accueil") },
                        label = { Text("Accueil") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == "events",
                        onClick = { navController.navigate("events") },
                        icon = { Icon(Icons.Default.Event, contentDescription = "Événements") },
                        label = { Text("Événements") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == "history",
                        onClick = { navController.navigate("history") },
                        icon = { Icon(Icons.Default.History, contentDescription = "Historique") },
                        label = { Text("Historique") }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "main",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("main") { MainScreen() }
            composable("history") { HistoryScreen() }
            composable("events") { EventsScreen(navController) }
            composable("event_details/{eventJson}") { backStackEntry ->
                val eventJson = backStackEntry.arguments?.getString("eventJson")
                Log.d("DEBUG", "📦 JSON reçu : $eventJson")
                EventDetailScreen(navController, eventJson)
            }
        }
    }
}
