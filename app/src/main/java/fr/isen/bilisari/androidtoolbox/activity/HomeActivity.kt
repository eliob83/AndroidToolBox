package fr.isen.bilisari.androidtoolbox.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import fr.isen.bilisari.androidtoolbox.R
import fr.isen.bilisari.androidtoolbox.database.Prefs
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
            startActivity(Intent(this@HomeActivity, StorageActivity::class.java))
        }

        imgPermissions.setOnClickListener {
            startActivity(Intent(this@HomeActivity, PermissionsActivity::class.java))
        }

        imgWebservices.setOnClickListener {
            //startActivity(Intent(this@HomeActivity, WebservicesActivity::class.java))
            startActivity(Intent(this@HomeActivity, UserRoomActivity::class.java))
        }


        btnLogout.setOnClickListener {
            // Remove session
            prefs.removeCredentials()
            Toast.makeText(this, resources.getText(R.string.home_logout_done), Toast.LENGTH_SHORT).show()

            // Back to login screen
            startActivity(Intent(this@HomeActivity, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
        }
    }
}