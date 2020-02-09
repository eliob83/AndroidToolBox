package fr.isen.bilisari.androidtoolbox.service

import android.content.Context
import android.database.Cursor
import android.provider.CalendarContract
import android.util.Log
import fr.isen.bilisari.androidtoolbox.model.Event
import java.util.*
import kotlin.collections.ArrayList


class CalendarService(private val context: Context) {

    fun getInstancesAsCursor(beginTime: Long, endTime: Long) : Cursor {
        return CalendarContract.Instances.query(context.contentResolver, EVENT_PROJECTION, beginTime, endTime)
    }


    fun getInstancesAsEvents(beginTime: Long, endTime: Long) : ArrayList<Event> {
        val cur = getInstancesAsCursor(beginTime, endTime)
        val events = ArrayList<Event>()

        while (cur.moveToNext()) {
            events.add(
                Event(
                    cur.getInt(EVENT_ID_INDEX),

                    cur.getString(EVENT_TITLE_INDEX),
                    cur.getString(EVENT_DESCRIPTION_INDEX),
                    cur.getString(EVENT_LOCATION_INDEX),

                    cur.getLong(EVENT_BEGIN_INDEX),
                    cur.getLong(EVENT_END_INDEX),

                    cur.getInt(EVENT_ALL_DAY_INDEX) != 0,
                    cur.getInt(EVENT_AVAILABILITY_INDEX) == 0
                )
            )
        }

        return events
    }

    fun getInstancesAsEvents(endTime: Long) : ArrayList<Event> {
        return getInstancesAsEvents(Calendar.getInstance().timeInMillis, endTime)
    }

    fun getInstancesAsEvents() : ArrayList<Event> {
        return getInstancesAsEvents(
            Calendar.getInstance().run {
                add(Calendar.WEEK_OF_MONTH, +1)
                timeInMillis
            }
        )
    }


    companion object {
        private val EVENT_PROJECTION: Array<String> = arrayOf(
            CalendarContract.Instances.EVENT_ID,

            CalendarContract.Instances.TITLE,
            CalendarContract.Instances.DESCRIPTION,
            CalendarContract.Instances.EVENT_LOCATION,

            CalendarContract.Instances.BEGIN,
            CalendarContract.Instances.END,

            CalendarContract.Instances.ALL_DAY,
            CalendarContract.Instances.AVAILABILITY
        )

        private const val EVENT_ID_INDEX = 0

        private const val EVENT_TITLE_INDEX = 1
        private const val EVENT_DESCRIPTION_INDEX = 2
        private const val EVENT_LOCATION_INDEX = 3

        private const val EVENT_BEGIN_INDEX = 4
        private const val EVENT_END_INDEX = 5

        private const val EVENT_ALL_DAY_INDEX = 6
        private const val EVENT_AVAILABILITY_INDEX = 7
    }
}