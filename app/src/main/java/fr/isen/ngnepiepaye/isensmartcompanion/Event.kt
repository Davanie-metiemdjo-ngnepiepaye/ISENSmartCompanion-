package fr.isen.ngnepiepaye.isensmartcompanion

data class Event(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val date: String = "",
    val location: String? = null,
    val category: String? = null
)
