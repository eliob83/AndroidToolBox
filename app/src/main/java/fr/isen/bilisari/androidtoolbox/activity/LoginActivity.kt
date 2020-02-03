package fr.isen.bilisari.androidtoolbox.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import fr.isen.bilisari.androidtoolbox.misc.Prefs
import fr.isen.bilisari.androidtoolbox.R
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    // SharedPreferences object
    private lateinit var prefs: Prefs


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        prefs = Prefs(this)

        // Credentials-saved check
        checkCredentials(prefs.username, prefs.password, false)


        // Listeners
        btnLogin.setOnClickListener {
            // Check credentials from user inputs
            checkCredentials(inputUsername.text.toString(), inputPassword.text.toString(), true)
        }
    }

    private fun checkCredentials(username: String, password: String, toasting: Boolean) {
        // Credentials check
        if (username == "damien" && password == "fontes") {
            // "Session" save
            prefs.setCredentials(username, password)

            Toast.makeText(this, resources.getString(R.string.login_success, username), Toast.LENGTH_SHORT).show()

            startActivity(Intent(this@LoginActivity, HomeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
        } else if (toasting) {
            Toast.makeText(this, R.string.login_fail, Toast.LENGTH_SHORT).show()
        }
    }
}