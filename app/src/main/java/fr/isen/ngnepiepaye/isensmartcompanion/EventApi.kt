package fr.isen.ngnepiepaye.isensmartcompanion

import retrofit2.http.GET
import retrofit2.Response

interface EventApi {
    @GET("events.json")
    suspend fun getEvents(): Response<List<Event>> // ✅ Correction : Firebase retourne une liste []
}
