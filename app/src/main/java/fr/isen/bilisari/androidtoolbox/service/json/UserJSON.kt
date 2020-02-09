package fr.isen.bilisari.androidtoolbox.service.json

import com.google.gson.Gson
import fr.isen.bilisari.androidtoolbox.model.User
import fr.isen.bilisari.androidtoolbox.service.Encryption
import java.io.File
import java.io.FileReader

// Path specification is mandatory
class UserJSON(path: String) {
    private val gson = Gson()

    // Path to JSON file to handle
    private var filepath: String = ""
    private var filepathEncoded: String = ""


    init {
        setFilepath(path)
    }


    // Set filepath of the JSON file to use
    fun setFilepath(path: String) : UserJSON {
        filepath = path + "user.json"
        filepathEncoded = path + "user_encoded.json"

        return this
    }


    fun encodedLoad(privateKey: String) : User {
        return gson.fromJson(Encryption.decrypt(FileReader(filepathEncoded).readText(), privateKey), User::class.java)
    }

    fun encodedSave(user: User, publicKey: String) : UserJSON {
        File(filepathEncoded).writeText(Encryption.encrypt(gson.toJson(user), publicKey))

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
    fun deleteUser(encoded: Boolean) : Boolean {
        return File(if (encoded) filepathEncoded else filepath).delete()
    }

    // Does the file exist
    fun exists(encoded: Boolean) : Boolean {
        return File(if (encoded) filepathEncoded else filepath).exists()
    }
}