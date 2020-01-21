package fr.isen.bilisari.androidtoolbox

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context) {
    private val PREFS_FILENAME = "fr.isen.bilisari.androidtoolbox.prefs"

    private val USERNAME = "user.username"
    private val PASSWORD = "user.password"

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE);


    var username: String?
        get() = prefs.getString(USERNAME, "")
        set(value) = prefs.edit().putString(USERNAME, value).apply()

    var password: String?
        get() = prefs.getString(PASSWORD, "")
        set(value) = prefs.edit().putString(PASSWORD, value).apply()


    fun setCredentials(parUsername: String?, parPassword: String?) {
        username = parUsername
        password = parPassword
    }

    fun removeCredentials() {
        prefs.edit().remove(USERNAME).remove(PASSWORD).apply()
    }
}