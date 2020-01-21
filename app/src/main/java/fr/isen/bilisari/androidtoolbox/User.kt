package fr.isen.bilisari.androidtoolbox

class User {
    var firstname: String = ""
    var surname: String = ""
    var birthdate: String = ""


    constructor() {}

    constructor(firstname: String, surname: String, birthdate: String) {
        this.firstname = firstname
        this.surname = surname
        this.birthdate = birthdate
    }

    override fun toString(): String {
        return "User $surname, $firstname from $birthdate"
    }
}