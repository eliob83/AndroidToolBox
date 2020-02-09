package fr.isen.bilisari.androidtoolbox.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.bilisari.androidtoolbox.R
import fr.isen.bilisari.androidtoolbox.activity.adapter.CalendarAdapter
import fr.isen.bilisari.androidtoolbox.service.CalendarService
import kotlinx.android.synthetic.main.activity_calendar.*


class CalendarActivity : AppCompatActivity() {

    private lateinit var adapter: CalendarAdapter
    private lateinit var service: CalendarService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        // Init events layout
        service = CalendarService(this)
        adapter = CalendarAdapter(ArrayList(), this)

        viewEvents.layoutManager = LinearLayoutManager(this)
        viewEvents.adapter = adapter


        updateEvents()


        // Listeners
        fabAdd.setOnClickListener {
            startActivity(Intent(this@CalendarActivity, AddEventActivity::class.java))
        }
    }


    fun updateEvents() {
        if (ActivityCompat.checkSelfPermission(this, PERMISSION_CALENDAR_READ) == PackageManager.PERMISSION_GRANTED) {
            adapter.updateItems(service.getInstancesAsEvents())
            adapter.notifyDataSetChanged()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(PERMISSION_CALENDAR_READ), PERMISSION_CODE_CALENDAR_READ)
        }
    }



    // Handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            when (requestCode) {
                PERMISSION_CODE_CALENDAR_READ -> {
                    updateEvents()
                }
            }
        } else {
            Toast.makeText(this, resources.getText(R.string.permissions_denied), Toast.LENGTH_SHORT).show()
        }
    }


    companion object {
        // Permissions
        private const val PERMISSION_CALENDAR_READ = Manifest.permission.READ_CALENDAR

        // Permission codes
        private const val PERMISSION_CODE_CALENDAR_READ = 11
    }
}