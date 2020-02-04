package fr.isen.bilisari.androidtoolbox.database

import com.google.gson.Gson
import fr.isen.bilisari.androidtoolbox.model.User
import java.io.File
import java.io.FileReader

// Path specification is mandatory
class UserJSON(path: String) {
    private val gson = Gson()

    // Path to JSON file to handle
    private var filepath: String = ""


    init {
        setFilepath(path)
    }


    // Set filepath of the JSON file to use
    private fun setFilepath(path: String) : UserJSON {
        filepath = path + "user.json"

        return this
    }



    // Save current user as JSON
    fun saveUser(user: User) : UserJSON {
        File(filepath).writeText(gson.toJson(user))

        return this
    }

    // Load JSON as current user
    fun loadUser() : User {
        return gson.fromJson(FileReader(filepath), User::class.java)
    }

    // Remove JSON
    fun deleteUser() : Boolean {
        return File(filepath).delete()
    }

    // Does the file exist
    fun exists() : Boolean {
        return File(filepath).exists()
    }
}