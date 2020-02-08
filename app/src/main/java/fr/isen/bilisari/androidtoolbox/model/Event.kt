package fr.isen.bilisari.androidtoolbox.model

import java.util.*

data class Event(var title: String, var description: String, var location: String, var dateStart: Date?, var dateEnd: Date?, var allDay: Boolean, var busy: Boolean) {
}