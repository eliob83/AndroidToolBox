package fr.isen.bilisari.androidtoolbox.activity.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.isen.bilisari.androidtoolbox.R
import fr.isen.bilisari.androidtoolbox.activity.TodoListActivity
import fr.isen.bilisari.androidtoolbox.model.Todo
import kotlinx.android.synthetic.main.item_todo_list.view.*


class TodoListAdapter(private var items : ArrayList<Todo>, private val context: TodoListActivity) : RecyclerView.Adapter<TodoListAdapter.ViewHolder>() {

    // Items count of the view
    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(items: ArrayList<Todo>) {
        this.items = items
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_todo_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textTitle.text = items[position].title
        holder.textDescription.text = items[position].description
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textTitle: TextView = view.textTitle
        val textDescription: TextView = view.textDescription
    }
}