package fr.isen.bilisari.androidtoolbox

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener {
            val username = inputUsername.editableText.toString()
            val password = inputPassword.editableText.toString()

            if (username == "damien" && password == "fontes") {
                Toast.makeText(this, "Bonjour $username", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
            } else {
                Toast.makeText(this, R.string.login_fail, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
