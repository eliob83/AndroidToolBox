package fr.isen.bilisari.androidtoolbox.activity

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import fr.isen.bilisari.androidtoolbox.R
import fr.isen.bilisari.androidtoolbox.model.User
import fr.isen.bilisari.androidtoolbox.misc.UserJSON
import kotlinx.android.synthetic.main.activity_storage.*
import java.text.SimpleDateFormat
import java.util.*


class StorageActivity : AppCompatActivity() {
    private lateinit var json : UserJSON


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage)

        json =
            UserJSON(cacheDir.absolutePath)

        // Popup alert
        val builder = AlertDialog.Builder(this@StorageActivity)
        builder.setTitle(R.string.storage_alert_title)


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
            DatePickerDialog(this@StorageActivity, dateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }


        // Listeners
        btnValidate.setOnClickListener {
            if (!inputFirstname.text.isNullOrEmpty() && !inputSurname.text.isNullOrEmpty() && inputBirthday.text != resources.getText(R.string.storage_birthday_default)) {
                json.saveUser(
                    User(
                        inputFirstname.text.toString(),
                        inputSurname.text.toString(),
                        inputBirthday.text.toString()
                    )
                )
                Toast.makeText(this, R.string.storage_form_valid, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, R.string.storage_form_invalid, Toast.LENGTH_SHORT).show()
            }
        }

        btnBack.setOnClickListener {
            startActivity(Intent(this@StorageActivity, HomeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }

        btnDelete.setOnClickListener {
            Toast.makeText(this, resources.getText(if (json.deleteUser()) R.string.storage_delete_done else R.string.storage_delete_error), Toast.LENGTH_SHORT).show()
        }

        btnRead.setOnClickListener {
            // File check
            if (json.exists()) {
                // Load user previously saved
                val user = json.loadUser()

                // Age calculation
                val arrBirthday = user.birthday.split("/")

                val now = Calendar.getInstance()
                val birthday = Calendar.getInstance()
                // January is month 0
                birthday.set(
                    Integer.parseInt(arrBirthday[2]),
                    Integer.parseInt(arrBirthday[1]) - 1,
                    Integer.parseInt(arrBirthday[0])
                )

                /*
                // Calculation by hand
                var age = 0L
                if (now.timeInMillis > birthday.timeInMillis) {
                    val diff = now - birthday
                    val seconds = diff / 1000
                    val minutes = seconds / 60
                    val hours = minutes / 60
                    val days = hours / 24
                    age = (days / 365)
                }
                */

                var age = now.get(Calendar.YEAR) - birthday.get(Calendar.YEAR)
                // If birthday had not already taken place this year
                if (now.get(Calendar.DAY_OF_YEAR) < birthday.get(Calendar.DAY_OF_YEAR)) {
                    age--
                }

                builder.setMessage(
                    resources.getString(
                        R.string.storage_alert_message,
                        user.surname,
                        user.firstname,
                        user.birthday,
                        age
                    )
                ).create().show()
            } else {
                Toast.makeText(
                    this,
                    resources.getText(R.string.storage_read_error),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    // Update birthday field in the form
    private fun updateInputBirthday(cal: Calendar) {
        inputBirthday.text = (SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH).format(cal.time))
    }
}