package fr.isen.bilisari.androidtoolbox.activity

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import fr.isen.bilisari.androidtoolbox.JSON
import fr.isen.bilisari.androidtoolbox.R
import fr.isen.bilisari.androidtoolbox.User
import kotlinx.android.synthetic.main.activity_register.*
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDate
import java.util.*


class RegisterActivity : AppCompatActivity() {

    var cal = Calendar.getInstance()


    private fun updateInputBirthdate() {
        inputBirthdate.setText(SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH).format(cal.time))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Popup alert
        val builder = AlertDialog.Builder(this@RegisterActivity)
        builder.setTitle(R.string.register_alert_title)
        //builder.setMessage("Nom: $")
        //builder.setNeutralButton("OK")

        // Inputs
        val dateSetListener = DatePickerDialog.OnDateSetListener{ _: DatePicker, year: Int, month: Int, day: Int ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, day)
            updateInputBirthdate()
        }


        inputBirthdate.setOnClickListener {
            DatePickerDialog(this@RegisterActivity, dateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }


        // Buttons
        btnValidate.setOnClickListener {
            Log.d("YESSAI", JSON(cacheDir.absolutePath).saveUser(User(inputFirstname.text.toString(), inputSurname.text.toString(), inputBirthdate.text.toString())).loadUser().toString())
        }

        btnBack.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, HomeActivity::class.java))
        }

        btnRead.setOnClickListener {
            val user = JSON(cacheDir.absolutePath).loadUser()

            val birthday = SimpleDateFormat("dd/MM/yyyy").parse(user.birthdate).time
            val now = Calendar.getInstance().timeInMillis

            var years = 0L
            if (now > birthday) {
                val diff = now - birthday
                val seconds = diff / 1000
                val minutes = seconds / 60
                val hours = minutes / 60
                val days = hours / 24
                years = (days / 365)
            }

            builder.setMessage("Nom: ${user.surname}\nPrénom: ${user.firstname}\nDate de naissance: ${birthday.toString()}\nÂge: $years").create().show()
        }
    }
}
