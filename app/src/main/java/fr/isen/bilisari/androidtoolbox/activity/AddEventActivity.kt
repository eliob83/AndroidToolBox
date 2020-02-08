package fr.isen.bilisari.androidtoolbox.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import fr.isen.bilisari.androidtoolbox.R
import kotlinx.android.synthetic.main.activity_add_event.*

class AddEventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)

        btnAdd.setOnClickListener {
            Toast.makeText(this, "Événement ajouté au calendrier", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@AddEventActivity, EventActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }
    }
}
