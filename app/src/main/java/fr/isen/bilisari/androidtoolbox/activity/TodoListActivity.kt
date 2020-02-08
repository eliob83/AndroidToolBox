package fr.isen.bilisari.androidtoolbox.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.bilisari.androidtoolbox.R

class TodoListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_list)
    }
}