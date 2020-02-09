package fr.isen.bilisari.androidtoolbox.misc

import java.text.SimpleDateFormat
import java.util.*

class DateMisc {
    companion object {
        fun datetimeFormat(time: Long): String {
            return SimpleDateFormat("dd/MM/yyyy, HH:mm", Locale.FRENCH).format(time)
        }

        fun dateFormat(time: Long): String {
            return SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH).format(time)
        }

        fun timeFormat(time: Long): String {
            return SimpleDateFormat("HH:mm", Locale.FRENCH).format(time)
        }
    }
}