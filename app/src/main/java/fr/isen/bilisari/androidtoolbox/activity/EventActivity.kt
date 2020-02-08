package fr.isen.bilisari.androidtoolbox.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.bilisari.androidtoolbox.R
import fr.isen.bilisari.androidtoolbox.activity.adapter.EventAdapter
import fr.isen.bilisari.androidtoolbox.model.Event
import kotlinx.android.synthetic.main.activity_event.*
import java.util.*
import kotlin.collections.ArrayList

class EventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        var arr = ArrayList<Event>()
        arr.addAll(arrayOf(
            Event("Enchanté", "Eh oui", "La Farlède", null, null, true, true),
            Event("Salut", "Eh non", "Tarascon", null, null, false, false)
        ))

        // Init events layout
        viewEvents.layoutManager = LinearLayoutManager(this)
        viewEvents.adapter = EventAdapter(arr,this)


        // Listeners
        fabAdd.setOnClickListener {
            startActivity(Intent(this@EventActivity, AddEventActivity::class.java))
        }
    }
}
