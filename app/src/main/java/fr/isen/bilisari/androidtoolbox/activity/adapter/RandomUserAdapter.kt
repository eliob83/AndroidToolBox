package fr.isen.bilisari.androidtoolbox.activity.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.bilisari.androidtoolbox.R
import fr.isen.bilisari.androidtoolbox.misc.RoundTransformation
import fr.isen.bilisari.androidtoolbox.model.RandomUser
import kotlinx.android.synthetic.main.item_webservices.view.*

class RandomUserAdapter(private val items : ArrayList<RandomUser>, private val context: Context) : RecyclerView.Adapter<RandomUserAdapter.ViewHolder>() {

    // Items count of the view
    override fun getItemCount(): Int {
        return items.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_webservices,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textName.text = items[position].name.toString()
        holder.textAddress.text = items[position].location.toString()
        holder.textEmail.text = items[position].email
        Picasso.get().load(items[position].picture?.large).transform(
            RoundTransformation(
                400f,
                0f
            )
        ).into(holder.imgPhoto)
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textName: TextView = view.textName
        val textAddress: TextView = view.textAddress
        val textEmail: TextView = view.textEmail
        val imgPhoto: ImageView = view.imgPhoto
    }
}