package fr.isen.bilisari.androidtoolbox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class LifecycleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("LIFECYCLE", "CREATE de l'activité")
        setContentView(R.layout.activity_lifecycle)
    }

    override fun onPause() {
        super.onPause()
        Log.d("LIFECYCLE", "PAUSE de l'activité")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("LIFECYCLE", "DESTROY de l'activité")
    }

    override fun onResume() {
        super.onResume()
        Log.d("LIFECYCLE", "RESUME de l'activité")
    }


    fun back(v: View) {
        val intent = Intent(this@LifecycleActivity, HomeActivity::class.java)
        startActivity(intent)
    }
}
