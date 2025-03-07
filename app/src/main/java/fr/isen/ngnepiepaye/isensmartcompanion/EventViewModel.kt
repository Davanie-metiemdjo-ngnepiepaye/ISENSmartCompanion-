package fr.isen.ngnepiepaye.isensmartcompanion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

class EventViewModel : ViewModel() {
    private val repository = EventRepository() // ✅ Assurez-vous que EventRepository.kt est bien défini
    private val _events = MutableLiveData<List<Event>>() // ✅ Assurez-vous que Event.kt est bien défini
    val events: LiveData<List<Event>> = _events

    fun fetchEvent() {
        viewModelScope.launch {
            try {
                val fetchedEvents = repository.getEvents()
                if (!fetchedEvents.isNullOrEmpty()) {
                    _events.value = fetchedEvents
                    println("✅ ${fetchedEvents.size} événements chargés depuis Firebase.")
                } else {
                    println("⚠️ Aucune donnée reçue de l'API.")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                println("❌ Erreur lors du chargement des événements : ${e.message}")
            }
        }
    }
}
