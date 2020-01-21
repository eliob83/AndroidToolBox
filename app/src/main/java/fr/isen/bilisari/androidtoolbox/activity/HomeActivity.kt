package fr.isen.bilisari.androidtoolbox.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.isen.bilisari.androidtoolbox.Prefs
import fr.isen.bilisari.androidtoolbox.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private var prefs : Prefs? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        prefs = Prefs(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        imgLifecycle.setOnClickListener {
            startActivity(Intent(this@HomeActivity, LifecycleActivity::class.java))
        }

        imgBackup.setOnClickListener {
            startActivity(Intent(this@HomeActivity, RegisterActivity::class.java))
        }

        imgPermissions.setOnClickListener {

        }

        imgWebservices.setOnClickListener {

        }

        btnLogout.setOnClickListener {
            prefs!!.removeCredentials()
            finish()
            startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
        }
    }
}
