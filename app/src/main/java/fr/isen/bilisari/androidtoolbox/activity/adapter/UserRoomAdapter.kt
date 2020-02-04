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
) : RecyclerView.Adapter<UserRoomAdapter.UserRoomViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var users = emptyList<User>()

    inner class UserRoomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textSurname: TextView = view.textSurname
        val textFirstname: TextView = view.textFirstname
        val textBirthday: TextView = view.textBirthday
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserRoomViewHolder {
        val itemView = inflater.inflate(R.layout.user_room_item, parent, false)
        return UserRoomViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserRoomViewHolder, position: Int) {
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