package fr.isen.ngnepiepaye.isensmartcompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            MainActivityScreeen(navController) // ✅ Correction : Passe bien `navController`
        }
    }
}

// ✅ Fonction qui gère la navigation avec NavHost
@Composable
fun AppNavigator() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainActivityScreeen(navController) }
        composable("events") { EventsScreen(navController) }
        composable("history") { HistoryScreen() }

        // ✅ Correction : Passage de l'événement en JSON pour EventDetailScreen
        composable("event_details/{eventJson}") { backStackEntry ->
            val eventJson = backStackEntry.arguments?.getString("eventJson")
            println("📦 JSON reçu dans EventDetailScreen : $eventJson") // 🔍 Vérification
            EventDetailScreen(navController, eventJson)
        }
    }
}
