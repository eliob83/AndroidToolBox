package fr.isen.bilisari.androidtoolbox

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        imgLifecycle.setOnClickListener {
            startActivity(Intent(this@HomeActivity, LifecycleActivity::class.java))
        }

        imgBackup.setOnClickListener {

        }

        imgPermissions.setOnClickListener {

        }

        imgWebservices.setOnClickListener {

        }

    }
}
