package fr.isen.bilisari.androidtoolbox.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import fr.isen.bilisari.androidtoolbox.R
import fr.isen.bilisari.androidtoolbox.model.Todo
import fr.isen.bilisari.androidtoolbox.service.firebase.TodoFirebase
import kotlinx.android.synthetic.main.activity_add_todo.*

class AddTodoActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_todo)

        database = TodoFirebase.getInstance(this).database

        btnAdd.setOnClickListener {
            if (!inputTitle.text.isNullOrEmpty()) {
                database.push().setValue(Todo(inputTitle.text.toString(), inputDescription.text.toString()))
                    .addOnFailureListener {
                        Toast.makeText(this, resources.getText(R.string.todo_add_error), Toast.LENGTH_SHORT).show()
                    }.addOnSuccessListener {
                        startActivity(
                            Intent(this@AddTodoActivity, TodoListActivity::class.java).addFlags(
                                Intent.FLAG_ACTIVITY_CLEAR_TOP
                            )
                        )
                    }
            } else {
                Toast.makeText(this, resources.getText(R.string.form_invalid), Toast.LENGTH_SHORT).show()
            }
        }
    }
}