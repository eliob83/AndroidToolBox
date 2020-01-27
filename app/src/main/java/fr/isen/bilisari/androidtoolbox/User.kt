package fr.isen.bilisari.androidtoolbox

class User {
    // User data
    var firstname: String = ""
    var surname: String = ""
    var birthday: String = ""


    constructor()
    constructor(parFirstname: String, parSurname: String, parBirthday: String) {
        firstname = parFirstname
        surname = parSurname
        birthday = parBirthday
    }


    override fun toString() : String {
        return "User $surname, $firstname born on $birthday"
    }
}