package fr.isen.bilisari.androidtoolbox.activity

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import fr.isen.bilisari.androidtoolbox.R
import fr.isen.bilisari.androidtoolbox.User
import fr.isen.bilisari.androidtoolbox.UserJSON
import kotlinx.android.synthetic.main.activity_register.*
import java.text.SimpleDateFormat
import java.util.*


class RegisterActivity : AppCompatActivity() {
    // Update birthday field in the form
    private fun updateInputBirthday(cal: Calendar) {
        inputBirthday.text = (SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH).format(cal.time))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Popup alert
        val builder = AlertDialog.Builder(this@RegisterActivity)
        builder.setTitle(R.string.register_alert_title)


        // Birthday input
        val cal = Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener{ _: DatePicker, year: Int, month: Int, day: Int ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, day)

            updateInputBirthday(cal)
        }
        // Open date picker on field click
        inputBirthday.setOnClickListener {
            DatePickerDialog(this@RegisterActivity, dateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }


        // Listeners
        btnValidate.setOnClickListener {
            if (!inputFirstname.text.isNullOrEmpty() && !inputSurname.text.isNullOrEmpty() && !inputBirthday.text.equals(R.string.register_birthday_default)) {
                UserJSON(cacheDir.absolutePath).saveUser(User(inputFirstname.text.toString(), inputSurname.text.toString(), inputBirthday.text.toString()))
                Toast.makeText(this, R.string.register_toast_valid, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, R.string.register_toast_invalid, Toast.LENGTH_SHORT).show()
            }
        }

        btnBack.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, HomeActivity::class.java))
        }

        btnRead.setOnClickListener {
            // Load user previously saved
            val user = UserJSON(cacheDir.absolutePath).loadUser()

            // Age calculation
            val arrBirthday = user.birthday.split("/")

            val now = Calendar.getInstance()
            val birthday = Calendar.getInstance()
            birthday.set(Integer.parseInt(arrBirthday[2]), Integer.parseInt(arrBirthday[1]), Integer.parseInt(arrBirthday[0]))

            /*
            // Calculation by hand
            var years = 0L
            if (now.timeInMillis > birthday.timeInMillis) {
                val diff = now - birthday
                val seconds = diff / 1000
                val minutes = seconds / 60
                val hours = minutes / 60
                val days = hours / 24
                years = (days / 365)
            }*/

            var age = now.get(Calendar.YEAR) - birthday.get(Calendar.YEAR)
            // If birthday had not already taken place this year (there is an awkward 31-days offset on the second DAY_OF_YEAR)
            if (now.get(Calendar.DAY_OF_YEAR) < birthday.get(Calendar.DAY_OF_YEAR)-31) {
                age--
            }

            builder.setMessage(
                    resources.getString(R.string.register_alert_message, user.surname, user.firstname, user.birthday, age)
             ).create().show()
        }
    }
}