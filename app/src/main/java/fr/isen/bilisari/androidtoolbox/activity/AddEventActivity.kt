package fr.isen.bilisari.androidtoolbox.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.provider.CalendarContract.Events
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import fr.isen.bilisari.androidtoolbox.R
import fr.isen.bilisari.androidtoolbox.misc.DateMisc
import kotlinx.android.synthetic.main.activity_add_event.*
import java.util.*


class AddEventActivity : AppCompatActivity() {

    private var beginMillis = 0L
    private var endMillis = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)

        // Listeners
        val calStart = Calendar.getInstance()
        val calEnd = Calendar.getInstance()

        inputDateStart.setOnClickListener {
            DatePickerDialog(this@AddEventActivity, { _, y, m, d ->
                calStart.set(y, m, d)

                inputDateStart.text = DateMisc.dateFormat(calStart.timeInMillis)
                beginMillis = calStart.timeInMillis
            }, calStart.get(Calendar.YEAR), calStart.get(Calendar.MONTH), calStart.get(Calendar.DAY_OF_MONTH)).show()
        }

        inputDateEnd.setOnClickListener {
            DatePickerDialog(this@AddEventActivity, { _: DatePicker, y: Int, m: Int, d: Int ->
                calEnd.set(y, m, d)

                inputDateEnd.text = DateMisc.dateFormat(calEnd.timeInMillis)
                endMillis = calEnd.timeInMillis
            }, calEnd.get(Calendar.YEAR), calEnd.get(Calendar.MONTH), calEnd.get(Calendar.DAY_OF_MONTH)).show()
        }


        inputTimeStart.setOnClickListener {
            TimePickerDialog(this@AddEventActivity, { _, h, m ->
                calStart.set(Calendar.HOUR_OF_DAY, h)
                calStart.set(Calendar.MINUTE, m)

                inputTimeStart.text = DateMisc.timeFormat(calStart.timeInMillis)
                beginMillis = calStart.timeInMillis
            }, calStart.get(Calendar.HOUR_OF_DAY), calStart.get(Calendar.MINUTE), true).show()
        }

        inputTimeEnd.setOnClickListener {
            TimePickerDialog(this@AddEventActivity, { _, h, m ->
                calEnd.set(Calendar.HOUR_OF_DAY, h)
                calEnd.set(Calendar.MINUTE, m)

                inputTimeEnd.text = DateMisc.timeFormat(calEnd.timeInMillis)
                endMillis = calEnd.timeInMillis
            }, calEnd.get(Calendar.HOUR_OF_DAY), calEnd.get(Calendar.MINUTE), true).show()
        }


        btnBack.setOnClickListener {
            startActivity(Intent(this@AddEventActivity, CalendarActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }


        btnAdd.setOnClickListener {
            if (beginMillis != 0L && endMillis != 0L) {
                startActivityForResult(
                    Intent(Intent.ACTION_EDIT)
                        .setData(Events.CONTENT_URI)
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginMillis)
                        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endMillis)
                        .putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, inputAllDay.isChecked)
                        .putExtra(Events.TITLE, inputTitle.text.toString())
                        .putExtra(Events.DESCRIPTION, inputDescription.text.toString())
                        .putExtra(Events.EVENT_LOCATION, inputLocation.text.toString())
                        .putExtra(
                            Events.AVAILABILITY,
                            if (inputBusy.isChecked) Events.AVAILABILITY_BUSY else Events.AVAILABILITY_FREE
                        )
                    , REQUEST_CODE_ADD
                )
            } else {
                Toast.makeText(this, resources.getText(R.string.form_invalid), Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_ADD) {
            // Seems to be no possibility to check event add result
            Toast.makeText(this, resources.getText(R.string.event_add_done), Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@AddEventActivity, CalendarActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }
    }


    companion object {
        private const val REQUEST_CODE_ADD = 11
    }
}
