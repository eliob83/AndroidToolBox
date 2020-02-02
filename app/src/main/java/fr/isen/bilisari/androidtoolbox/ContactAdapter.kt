package fr.isen.bilisari.androidtoolbox

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.isen.bilisari.androidtoolbox.model.Contact
import kotlinx.android.synthetic.main.contact_list_item.view.*

class ContactAdapter(private val items : ArrayList<Contact>, private val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.contact_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.textName?.text = items.get(position).name
        holder?.textPhone?.text = items.get(position).phone
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val textName = view.textName
    val textPhone = view.textPhone
}