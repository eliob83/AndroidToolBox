package fr.isen.bilisari.androidtoolbox.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    val firstname: String,
    val surname: String,
    val birthday: String) {

    @PrimaryKey(autoGenerate = true) var id: Int = 0

    constructor(firstname: String, surname: String, birthday: String, id: Int) : this(firstname, surname, birthday) {
        this.id = id
    }

    override fun toString() : String {
        return "User $surname, $firstname born on $birthday"
    }
}