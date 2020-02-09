package fr.isen.bilisari.androidtoolbox.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.bilisari.androidtoolbox.R
import fr.isen.bilisari.androidtoolbox.activity.adapter.TodoListAdapter
import fr.isen.bilisari.androidtoolbox.model.Todo
import kotlinx.android.synthetic.main.activity_todo_list.*

class TodoListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_list)


        viewTodo.layoutManager = LinearLayoutManager(this)
        viewTodo.adapter = TodoListAdapter(arrayListOf(
            Todo(1, "Tâche 1", "Eh oué"),
            Todo(2, "Tâche 2", "La dernière !")
        ), this)


        // Listeners
        fabAdd.setOnClickListener {
            startActivity(Intent(this@TodoListActivity, AddTodoActivity::class.java))
        }
    }
}