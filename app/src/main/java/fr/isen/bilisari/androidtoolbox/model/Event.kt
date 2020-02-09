package fr.isen.bilisari.androidtoolbox.model

import java.util.*

data class Event(
    var id: Int,

    var title: String?,
    var description: String?,
    var location: String?,

    var begin: Long,
    var end: Long,

    var allDay: Boolean,
    var busy: Boolean)