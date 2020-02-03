package fr.isen.bilisari.androidtoolbox.model

class RandomUser {
    var gender: String? = null

    var name: Name? = null
    var location: Location? = null

    var email: String? = null

    var picture: Picture? = null


    class Name {
        var title: String? = null
        var first: String? = null
        var last: String? = null

        override fun toString() : String {
            return "$first $last"
        }
    }

    class Location {
        var street: Street? = null
        var city: String? = null
        var state: String? = null
        var postcode: Int? = null

        class Street {
            var number: Int? = null
            var name: String? = null

            override fun toString() : String {
                return "$number $name"
            }
        }

        override fun toString() : String {
            return "$street, $postcode $city"
        }
    }

    class Picture {
        var large: String? = null
        var medium: String? = null
        var thumbnail: String? = null
    }
}