package fr.isen.bilisari.androidtoolbox.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.Response.Listener
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.bilisari.androidtoolbox.R
import fr.isen.bilisari.androidtoolbox.activity.adapter.RandomUserAdapter
import fr.isen.bilisari.androidtoolbox.model.RandomUser
import kotlinx.android.synthetic.main.activity_webservices.*

class WebservicesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webservices)

        // Init users layout
        viewUsers.layoutManager = LinearLayoutManager(this)


        getRandoms(12)
    }

    private fun getRandoms(count: Int) {
        val queue = Volley.newRequestQueue(this)

        val request = JsonObjectRequest(Request.Method.GET, getUrl(count), null,
            Listener { response ->
                val users = ArrayList<RandomUser>()
                val json = response.getJSONArray("results")

                for (i in 0 until json.length()) {
                    users.add(Gson().fromJson(json[i].toString(), RandomUser::class.java))
                }

                viewUsers.adapter = RandomUserAdapter(users, this)
            },
            Response.ErrorListener { error ->
                Log.d("WEBSERVICES", "ERROR on API request: $error")
                Toast.makeText(this, resources.getString(R.string.webservices_api_error, error), Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(request)
    }


    companion object {
        private fun getUrl(count: Int) : String { return getUrl(count, "fr") }
        private fun getUrl(nationality: String) : String { return getUrl(10, nationality) }

        private fun getUrl(count: Int, nationality: String) : String {
            return "$API_URL?results=$count&nat=$nationality"
        }

        private const val API_URL = "https://randomuser.me/api/"
    }
}