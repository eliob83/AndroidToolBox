package fr.isen.bilisari.androidtoolbox.activity

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import fr.isen.bilisari.androidtoolbox.R
import fr.isen.bilisari.androidtoolbox.misc.DateMisc
import fr.isen.bilisari.androidtoolbox.model.User
import fr.isen.bilisari.androidtoolbox.service.Encryption
import fr.isen.bilisari.androidtoolbox.service.json.UserJSON
import fr.isen.bilisari.androidtoolbox.service.room.UserRoomDatabase
import fr.isen.bilisari.androidtoolbox.service.sharedpreferences.Prefs
import kotlinx.android.synthetic.main.activity_storage.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class StorageActivity : AppCompatActivity() {
    private lateinit var json : UserJSON
    private lateinit var room : UserRoomDatabase
    private lateinit var prefs: Prefs


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage)

        json = UserJSON(cacheDir.absolutePath)
        room = UserRoomDatabase.getDatabase(this)
        prefs = Prefs(this)

        fab.bringToFront()

        // Popup alert
        val builder = AlertDialog.Builder(this@StorageActivity)
        builder.setTitle(R.string.storage_alert_title)


        // Birthday input
        val cal = Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener{ _: DatePicker, year: Int, month: Int, day: Int ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, day)

            inputBirthday.text = DateMisc.dateFormat(cal.timeInMillis)
        }
        // Open date picker on field click
        inputBirthday.setOnClickListener {
            DatePickerDialog(this@StorageActivity, dateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        // FAB Listeners
        fab.addOnMenuItemClickListener { _, _, itemId ->
            when (itemId) {
                R.id.storage_fab_add_json -> saveUser(false)
                R.id.storage_fab_add_room -> saveUser(true)
                R.id.storage_fab_delete_json ->
                    Toast.makeText(this, resources.getString(if (json.deleteUser(switchSecure.isChecked)) R.string.storage_delete_done else R.string.storage_delete_error, "JSON"), Toast.LENGTH_SHORT).show()
                R.id.storage_fab_delete_room -> {
                    GlobalScope.launch { room.userDao().deleteAll() }
                    Toast.makeText(
                        this,
                        resources.getString(R.string.storage_delete_done, "Room"),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                R.id.storage_fab_show_json -> loadUser(builder)
                R.id.storage_fab_show_room -> startActivity(Intent(this@StorageActivity, UserRoomActivity::class.java))
            }
        }

        btnBack.setOnClickListener {
            startActivity(Intent(this@StorageActivity, HomeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }

        switchSecure.setOnClickListener {
            if (switchSecure.isChecked) {
                fab.getMiniFab(0).setImageResource(R.drawable.ic_security_black_24dp)
                fab.getMiniFabTextView(0).text = resources.getText(R.string.storage_fab_add_json_secure)
                fab.getMiniFabTextView(2).text = resources.getText(R.string.storage_fab_show_json_secure)
                fab.getMiniFabTextView(4).text = resources.getText(R.string.storage_fab_delete_json_secure)
            } else {
                fab.getMiniFab(0).setImageResource(R.drawable.ic_person_add_black_24dp)
                fab.getMiniFabTextView(0).text = resources.getText(R.string.storage_fab_add_json)
                fab.getMiniFabTextView(2).text = resources.getText(R.string.storage_fab_show_json)
                fab.getMiniFabTextView(4).text = resources.getText(R.string.storage_fab_delete_json)
            }
        }
    }


    private fun saveUser(saveInRoom: Boolean){
        if (!inputFirstname.text.isNullOrEmpty() && !inputSurname.text.isNullOrEmpty() && isDateValid(inputBirthday.text.toString())) {
            val user = User(
                inputFirstname.text.toString(),
                inputSurname.text.toString(),
                inputBirthday.text.toString()
            )

            if (saveInRoom)
                GlobalScope.launch { room.userDao().insert(user) }
            else {
                if (switchSecure.isChecked) {
                    prefs.setKeys(Encryption.generateKeyPair())
                    json.encodedSave(user, prefs.publicKey)

                    Toast.makeText(this, resources.getText(R.string.storage_form_valid_secure), Toast.LENGTH_SHORT).show()
                    return
                } else {
                    json.saveUser(user)
                }
            }

            Toast.makeText(this, resources.getString(R.string.storage_form_valid, if (saveInRoom) "Room" else "JSON"), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, resources.getText(R.string.form_invalid), Toast.LENGTH_SHORT).show()
        }
    }

    private fun isDateValid(input: String) : Boolean {
        if (input != resources.getText(R.string.date_default)) {
            val arr = input.split("/")

            val old = Calendar.getInstance()
            old.set(arr[2].toInt(), arr[1].toInt()-1, arr[0].toInt(), 0, 0, 0)

            val now = Calendar.getInstance()
            now.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH), 0, 0, 0)

            return now.timeInMillis > old.timeInMillis
        }

        return false
    }

    private fun loadUser(builder: AlertDialog.Builder) {
        // File check
        if (json.exists(switchSecure.isChecked)) {
            // Load user previously saved
            val user = if (switchSecure.isChecked) json.encodedLoad(prefs.privateKey) else json.loadUser()

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