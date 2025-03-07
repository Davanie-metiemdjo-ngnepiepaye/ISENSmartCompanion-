package fr.isen.ngnepiepaye.isensmartcompanion

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EventRepository {
    private val eventApi: EventApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://isen-smart-companion-default-rtdb.europe-west1.firebasedatabase.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        eventApi = retrofit.create(EventApi::class.java)
    }

    suspend fun getEvents(): List<Event> {
        return try {
            val response = eventApi.getEvents()
            if (response.isSuccessful) {
                response.body() ?: emptyList() // ✅ Correction : Directement une List<Event>
            } else {
                Log.e("API_ERROR", "Erreur API : ${response.errorBody()?.string()}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("API_ERROR", "Erreur lors de la récupération des événements : ${e.message}", e)
            emptyList()
        }
    }
}