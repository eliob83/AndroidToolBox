package fr.isen.bilisari.androidtoolbox.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import fr.isen.bilisari.androidtoolbox.R
import fr.isen.bilisari.androidtoolbox.fragment.LifecycleFragment
import fr.isen.bilisari.androidtoolbox.fragment.LifecycleFragmentBis
import kotlinx.android.synthetic.main.activity_lifecycle.*


class LifecycleActivity : AppCompatActivity() {

    // Log function
    private fun cycle(state: String) {
        Log.d("LIFECYCLE", state)
        // Set state textview of the activity
        textState.text = state
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycle)

        // Slide viewer
        val pager = PagerAdapter(supportFragmentManager)
        viewPager.adapter = pager

        /*
        // Initial fragment hide in case of show/hide mechanism (need to be set in XML activity)
        supportFragmentManager.beginTransaction().hide(fragment).commit()
        */

        val fragment = LifecycleFragment()
        val fragmentBis = LifecycleFragmentBis()

        // Initial fragment add
        supportFragmentManager.beginTransaction().add(R.id.layoutFragment, fragment).commit()

        // Listeners
        btnSwitch.setOnClickListener {
            Log.d("LIFECYCLE", "alternate")

            /*
            // Simple hide/show mechanism
            if (fragmentBis.isVisible) {
                supportFragmentManager.beginTransaction().show(fragment).commit()
                supportFragmentManager.beginTransaction().hide(fragmentBis).commit()
            } else {
                supportFragmentManager.beginTransaction().hide(fragment).commit()
                supportFragmentManager.beginTransaction().show(fragmentBis).commit()
            }*/

            /*
            // Add/remove mechanism
            if (fragmentBis.isVisible) {
                supportFragmentManager.beginTransaction().remove(fragmentBis).commit()
                supportFragmentManager.beginTransaction().add(R.id.layoutFragment, fragment).commit()
            } else {
                supportFragmentManager.beginTransaction().remove(fragment).commit()
                supportFragmentManager.beginTransaction().add(R.id.layoutFragment, fragmentBis).commit()
            }*/

            // Replace mechanism
            supportFragmentManager.beginTransaction().replace(R.id.layoutFragment, (if (fragment.isVisible) fragmentBis else fragment)).commit()
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


    private inner class PagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int = PAGER_COUNT

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> LifecycleFragment()
                else -> LifecycleFragmentBis()
            }
        }
    }

    companion object {
        private const val PAGER_COUNT = 2
    }
}