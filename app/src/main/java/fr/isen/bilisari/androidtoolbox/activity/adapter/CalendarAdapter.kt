package fr.isen.bilisari.androidtoolbox.activity.adapter

import android.view.LayoutInflater.from
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.isen.bilisari.androidtoolbox.R
import fr.isen.bilisari.androidtoolbox.activity.CalendarActivity
import fr.isen.bilisari.androidtoolbox.misc.DateMisc
import fr.isen.bilisari.androidtoolbox.model.Event
import kotlinx.android.synthetic.main.item_calendar.view.*

class CalendarAdapter(private var items : ArrayList<Event>, private val context: CalendarActivity) : RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {

    // Items count of the view
    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(items: ArrayList<Event>) {
        this.items = items
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            from(context).inflate(
                R.layout.item_calendar,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textTitle.text = items[position].title
        holder.textDescription.text = items[position].description
        holder.textLocation.text = items[position].location

        if (items[position].busy) {
            holder.imgBusy.setImageResource(R.drawable.ic_do_not_disturb_on_black_24dp)
            holder.imgBusy.setColorFilter(context.resources.getColor(R.color.colorDarkRed))
            holder.textBusy.text = context.resources.getText(R.string.event_busy)
        } else {
            holder.imgBusy.setImageResource(R.drawable.ic_do_not_disturb_off_black_24dp)
            holder.imgBusy.setColorFilter(context.resources.getColor(R.color.colorDarkGreen))
            holder.textBusy.text = context.resources.getText(R.string.event_free)
        }

        if (items[position].allDay) {
            holder.textDateStart.text = context.resources.getString(R.string.event_entire_date_start, DateMisc.dateFormat(items[position].begin))
            holder.textDateEnd.text = DateMisc.dateFormat(items[position].end)
        } else {
            holder.textDateStart.text = DateMisc.datetimeFormat(items[position].begin)
            holder.textDateEnd.text = DateMisc.datetimeFormat(items[position].end)
        }
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textTitle: TextView = view.textTitle
        val textDescription: TextView = view.textDescription
        val textLocation: TextView = view.textLocation

        val textDateStart: TextView = view.textDateStart
        val textDateEnd: TextView = view.textDateEnd

        val imgBusy: ImageView = view.imgBusy
        val textBusy: TextView = view.textBusy
    }
}