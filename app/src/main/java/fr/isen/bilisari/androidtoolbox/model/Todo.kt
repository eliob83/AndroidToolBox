package fr.isen.bilisari.androidtoolbox.model

class Todo {
    var title: String = "Titre"
    var description: String = "Description"

    constructor()

    constructor(title: String, description: String) {
        this.title = title
        this.description = description
    }
}