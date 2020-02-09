package fr.isen.bilisari.androidtoolbox.activity.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.isen.bilisari.androidtoolbox.R
import fr.isen.bilisari.androidtoolbox.model.Todo

class TodoListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<TodoListAdapter.UserRoomViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var todos = emptyList<Todo>()

    inner class UserRoomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        /*val textSurname: TextView = view.textSurname
        val textFirstname: TextView = view.textFirstname
        val textBirthday: TextView = view.textBirthday*/
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserRoomViewHolder {
        val itemView = inflater.inflate(R.layout.item_todo_list, parent, false)
        return UserRoomViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserRoomViewHolder, position: Int) {
        /*holder.textSurname.text = users[position].surname
        holder.textFirstname.text = users[position].firstname
        holder.textBirthday.text = users[position].birthday*/
    }

    internal fun setUsers(todos: List<Todo>) {
        this.todos = todos
        notifyDataSetChanged()
    }

    override fun getItemCount() = todos.size
}