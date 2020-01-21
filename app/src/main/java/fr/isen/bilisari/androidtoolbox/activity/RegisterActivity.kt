package fr.isen.bilisari.androidtoolbox.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import fr.isen.bilisari.androidtoolbox.JSON
import fr.isen.bilisari.androidtoolbox.R
import fr.isen.bilisari.androidtoolbox.User
import kotlinx.android.synthetic.main.activity_register.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class RegisterActivity : AppCompatActivity() {

    var cal = Calendar.getInstance()


    private fun updateInputBirthdate() {
        inputBirthdate.setText(SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH).format(cal.time))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


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
            Log.d("YESSAI", JSON(cacheDir.absolutePath).loadUser().toString())
        }
    }
}
