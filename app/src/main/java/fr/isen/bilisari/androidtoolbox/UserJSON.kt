package fr.isen.bilisari.androidtoolbox

import com.google.gson.Gson
import java.io.File
import java.io.FileReader

class UserJSON {
    private val gson = Gson()

    // Path to JSON file to handle
    private var filepath: String = ""

    // JSON-related user
    var user: User = User()


    // Path specification is mandatory
    constructor(path: String) {
        setFilepath(path)
    }


    // Set filepath of the JSON file to use
    fun setFilepath(path: String) : UserJSON {
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
}