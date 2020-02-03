package fr.isen.bilisari.androidtoolbox.database

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context) {
    // Current SharedPreferences
    private val prefs: SharedPreferences = context.getSharedPreferences(filename, Context.MODE_PRIVATE)


    // Username of the saved user
    var username: String
        get() = prefs.getString(fieldUsername, "").orEmpty()
        set(value) = prefs.edit().putString(fieldUsername, value).apply()

    // Password of the saved user
    var password: String
        get() = prefs.getString(fieldPassword, "").orEmpty()
        set(value) = prefs.edit().putString(fieldPassword, value).apply()


    // Setter shortcut
    fun setCredentials(parUsername: String, parPassword: String) {
        username = parUsername
        password = parPassword
    }

    // Delete credentials from SharedPreferences
    fun removeCredentials() {
        prefs.edit().remove(fieldUsername).remove(
            fieldPassword
        ).apply()
    }


    companion object {
        // Filename of SharedPreferences to use
        private const val filename = "fr.isen.bilisari.androidtoolbox.prefs"

        // Field names in the SharedPreferences
        private const val fieldUsername = "user.username"
        private const val fieldPassword = "user.password"
    }
}