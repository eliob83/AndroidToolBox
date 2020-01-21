package fr.isen.bilisari.androidtoolbox

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_lifecycle.*


class LifecycleActivity : AppCompatActivity(), LifecycleFragment.OnFragmentInteractionListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycle)

        val fragment = LifecycleFragment()
        val fragmentBis = LifecycleFragmentBis()
        supportFragmentManager.beginTransaction().add(R.id.layoutFragment, fragment).commit()
        //supportFragmentManager.beginTransaction().hide(fragment).commit()

        btnSwitch.setOnClickListener {
            Log.d("LIFECYCLE", "alternate")

            // Simple hide/show
            /*
            if (fragmentBis.isVisible) {
                supportFragmentManager.beginTransaction().show(fragment).commit()
                supportFragmentManager.beginTransaction().hide(fragmentBis).commit()
            } else {
                supportFragmentManager.beginTransaction().hide(fragment).commit()
                supportFragmentManager.beginTransaction().show(fragmentBis).commit()
            }*/

            // Replace
            supportFragmentManager.beginTransaction().replace(R.id.layoutFragment, (if (fragment.isVisible) fragmentBis else fragment)).commit()

            // Add/remove
            /*if (fragmentBis.isVisible) {
                supportFragmentManager.beginTransaction().remove(fragmentBis).commit()
                supportFragmentManager.beginTransaction().add(R.id.layoutFragment, fragment).commit()
            } else {
                supportFragmentManager.beginTransaction().remove(fragment).commit()
                supportFragmentManager.beginTransaction().add(R.id.layoutFragment, fragmentBis).commit()
            }*/
        }

        Log.d("LIFECYCLE", "CREATE de l'activité")
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
}
