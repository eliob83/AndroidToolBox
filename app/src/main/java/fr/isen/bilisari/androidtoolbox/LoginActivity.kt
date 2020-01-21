package fr.isen.bilisari.androidtoolbox

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    var prefs: Prefs? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        prefs = Prefs(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Recharge des identifiants
        checkCredentials(prefs!!.username, prefs!!.password, false)


        btnLogin.setOnClickListener {
            checkCredentials(inputUsername.text.toString(), inputPassword.text.toString(), true)
        }

        btnRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }

    private fun checkCredentials(username: String?, password: String?, toasting: Boolean) {
        // Check des identifiants
        if (username == "damien" && password == "fontes") {
            // Sauvegarde de la "session"
            prefs?.username = username
            prefs?.password = password

            if (toasting)
                Toast.makeText(this, "Enchanté $username", Toast.LENGTH_SHORT).show()

            finish()
            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
        } else {
            if (toasting)
                Toast.makeText(this, R.string.login_fail, Toast.LENGTH_SHORT).show()
        }
    }
}
