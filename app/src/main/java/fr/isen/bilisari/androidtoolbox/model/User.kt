package fr.isen.bilisari.androidtoolbox.model

class User(var firstname: String, var surname: String, var birthday: String) {

    override fun toString() : String {
        return "User $surname, $firstname born on $birthday"
    }
}