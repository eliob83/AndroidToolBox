package fr.isen.bilisari.androidtoolbox.fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import fr.isen.bilisari.androidtoolbox.R
import kotlinx.android.synthetic.main.activity_lifecycle.*

class LifecycleFragmentBis : Fragment() {
    private fun cycle(state: String) {
        Log.d("++FRAGMENT", state)
        textState?.text = state
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cycle("CREATE")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lifecycle_bis, container, false)
    }


    override fun onPause() {
        super.onPause()
        cycle("PAUSE")
    }

    override fun onResume() {
        super.onResume()
        cycle("RESUME")
    }

    override fun onDestroy() {
        super.onDestroy()
        cycle("DESTROY")
    }
}