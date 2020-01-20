package fr.isen.bilisari.androidtoolbox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    fun lifecycle(v: View) {
        val intent = Intent(this@HomeActivity, LifecycleActivity::class.java)
        startActivity(intent)
    }

    fun backup(v: View) {

    }

    fun permissions(v: View) {

    }

    fun webservices(v: View) {

    }
}
