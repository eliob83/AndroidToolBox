package fr.isen.bilisari.androidtoolbox.service.firebase

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase

class TodoFirebase(private val context: Context) {
    var database = FirebaseDatabase.getInstance().reference.child("todo")

    init {
        FirebaseApp.initializeApp(this.context)
    }


    companion object {
        // Singleton prevents multiple instances of database opening at the same time
        @Volatile
        private var INSTANCE: TodoFirebase? = null

        fun getInstance(context: Context): TodoFirebase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = TodoFirebase(context.applicationContext)

                INSTANCE = instance
                return instance
            }
        }
    }
}