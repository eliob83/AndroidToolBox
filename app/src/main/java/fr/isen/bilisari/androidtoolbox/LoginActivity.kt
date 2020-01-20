package fr.isen.bilisari.androidtoolbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import android.content.Intent


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }


    fun validateForm(v: View) {
        if (this.username.editableText.toString().equals("damien") && this.password.editableText.toString().equals("fontes")) {
            Toast.makeText(
                this,
                "Bonjour " + this.username.editableText.toString() + " !",
                Toast.LENGTH_LONG
            ).show()

            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
            startActivity(intent)
        } else
            Toast.makeText(this, R.string.login_fail, Toast.LENGTH_LONG).show()
    }
}
