package fr.isen.bilisari.androidtoolbox.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import fr.isen.bilisari.androidtoolbox.R
import fr.isen.bilisari.androidtoolbox.activity.adapter.TodoListAdapter
import fr.isen.bilisari.androidtoolbox.model.Todo
import fr.isen.bilisari.androidtoolbox.service.firebase.TodoFirebase
import kotlinx.android.synthetic.main.activity_todo_list.*

class TodoListActivity : AppCompatActivity(), ChildEventListener {
    private lateinit var database: DatabaseReference
    private lateinit var adapter: TodoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_list)

        adapter = TodoListAdapter(HashMap(), this)

        database = TodoFirebase.getInstance(this).database
        database.addChildEventListener(this)

        viewTodo.layoutManager = LinearLayoutManager(this)
        viewTodo.adapter = adapter

        // Listeners
        fabAdd.setOnClickListener {
            startActivity(Intent(this@TodoListActivity, AddTodoActivity::class.java))
        }
    }

    override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
        adapter.addItem(dataSnapshot.key, dataSnapshot.getValue(Todo::class.java))
    }

    override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
        adapter.changeItem(dataSnapshot.key, dataSnapshot.getValue(Todo::class.java))
    }

    override fun onChildRemoved(dataSnapshot: DataSnapshot) {
        adapter.removeItem(dataSnapshot.key)
    }

    override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {}

    override fun onCancelled(databaseError: DatabaseError) {
        Toast.makeText(this@TodoListActivity, resources.getText(R.string.todo_get_error), Toast.LENGTH_SHORT).show()
    }
}