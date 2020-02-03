package fr.isen.bilisari.androidtoolbox.activity.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.isen.bilisari.androidtoolbox.R
import fr.isen.bilisari.androidtoolbox.model.User
import kotlinx.android.synthetic.main.user_room_item.view.*

class UserRoomAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<UserRoomAdapter.WordViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var users = emptyList<User>()

    inner class WordViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textSurname: TextView = view.textSurname
        val textFirstname: TextView = view.textFirstname
        val textBirthday: TextView = view.textBirthday
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = inflater.inflate(R.layout.user_room_item, parent, false)
        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.textSurname.text = users[position].surname
        holder.textFirstname.text = users[position].firstname
        holder.textBirthday.text = users[position].birthday
    }

    internal fun setUsers(users: List<User>) {
        this.users = users
        notifyDataSetChanged()
    }

    override fun getItemCount() = users.size
}