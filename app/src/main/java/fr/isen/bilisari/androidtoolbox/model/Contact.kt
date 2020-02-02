package fr.isen.bilisari.androidtoolbox.model

data class Contact(var name: String, var phone: String) {
    constructor() : this("", "")
}