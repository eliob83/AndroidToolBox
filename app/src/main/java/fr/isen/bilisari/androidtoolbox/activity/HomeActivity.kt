package fr.isen.bilisari.androidtoolbox.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.isen.bilisari.androidtoolbox.Prefs
import fr.isen.bilisari.androidtoolbox.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    // SharedPreferences object
    private lateinit var prefs: Prefs


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        prefs = Prefs(this)

        // Listeners
        imgLifecycle.setOnClickListener {
            startActivity(Intent(this@HomeActivity, LifecycleActivity::class.java))
        }

        imgBackup.setOnClickListener {
            startActivity(Intent(this@HomeActivity, RegisterActivity::class.java))
        }

        imgPermissions.setOnClickListener {
            startActivity(Intent(this@HomeActivity, InfosActivity::class.java))
        }

        imgWebservices.setOnClickListener {

        }


        btnLogout.setOnClickListener {
            // Remove session
            prefs.removeCredentials()
            // Back to login screen
            finish()
            startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
        }
    }
}