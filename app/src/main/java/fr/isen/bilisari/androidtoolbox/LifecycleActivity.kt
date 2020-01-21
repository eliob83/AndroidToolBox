package fr.isen.bilisari.androidtoolbox

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_lifecycle.*


class LifecycleActivity : AppCompatActivity(), LifecycleFragment.OnFragmentInteractionListener {
    fun cycle(state: String) {
        Log.d("LIFECYCLE", state)
        textState.text = state
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycle)
        //supportFragmentManager.beginTransaction().hide(fragment).commit()


        val fragment = LifecycleFragment()
        val fragmentBis = LifecycleFragmentBis()
        supportFragmentManager.beginTransaction().add(R.id.layoutFragment, fragment).commit()
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

        cycle("CREATE")
    }


    override fun onPause() {
        super.onPause()
        cycle("PAUSE")
    }

    override fun onDestroy() {
        super.onDestroy()
        cycle("DESTROY")
    }

    override fun onResume() {
        super.onResume()
        cycle("RESUME")
    }
}
