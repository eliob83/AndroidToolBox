package fr.isen.bilisari.androidtoolbox.activity.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.LayoutInflater.*
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.isen.bilisari.androidtoolbox.R
import fr.isen.bilisari.androidtoolbox.model.Event
import kotlinx.android.synthetic.main.event_list_item.view.*
import org.w3c.dom.Text

class EventAdapter(private val items : ArrayList<Event>, private val context: Context) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    // Items count of the view
    override fun getItemCount(): Int {
        return items.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            from(context).inflate(
                R.layout.event_list_item,
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
        }

        holder.textDateStart.text = items[position].dateStart.toString()
        holder.textDateEnd.text = if (items[position].allDay) context.resources.getText(R.string.event_all_day) else items[position].dateEnd.toString()
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