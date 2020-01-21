package fr.isen.bilisari.androidtoolbox

import com.google.gson.Gson
import java.io.File
import java.io.FileReader

class JSON {
    private var filepath: String = ""
    private val gson = Gson()

    var user: User = User()

    constructor() {}
    constructor(path: String) {
        setFilepath(path)
    }


    fun setFilepath(path: String): JSON {
        filepath = path + "user.json"

        return this
    }


    fun saveUser(user: User): JSON {
        File(filepath).writeText(gson.toJson(user))

        return this
    }

    fun loadUser(): User {
        return gson.fromJson(FileReader(filepath), User::class.java)
    }
}