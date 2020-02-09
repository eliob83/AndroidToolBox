package fr.isen.bilisari.androidtoolbox.activity.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.isen.bilisari.androidtoolbox.R
import fr.isen.bilisari.androidtoolbox.model.Contact
import kotlinx.android.synthetic.main.item_contact.view.*

class ContactAdapter(private val items : ArrayList<Contact>, private val context: Context) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    // Items count of the view
    override fun getItemCount(): Int {
        return items.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_contact,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textName.text = items[position].name
        holder.textPhone.text = items[position].phone
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textName: TextView = view.textName
        val textPhone: TextView = view.textPhone
    }
}