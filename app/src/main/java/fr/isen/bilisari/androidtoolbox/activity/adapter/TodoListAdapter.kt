package fr.isen.bilisari.androidtoolbox.activity.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.isen.bilisari.androidtoolbox.R
import fr.isen.bilisari.androidtoolbox.activity.TodoListActivity
import fr.isen.bilisari.androidtoolbox.model.Todo
import fr.isen.bilisari.androidtoolbox.service.firebase.TodoFirebase
import kotlinx.android.synthetic.main.activity_todo_list.*
import kotlinx.android.synthetic.main.item_todo_list.view.*


class TodoListAdapter(private var items : HashMap<String, Todo>, private val context: TodoListActivity) : RecyclerView.Adapter<TodoListAdapter.ViewHolder>() {
    private val database = TodoFirebase.getInstance(context).database

    // Items count of the view
    override fun getItemCount(): Int {
        return items.size
    }

    fun addItem(key: String?, todo: Todo?) {
        if (key != null && todo != null) {
            items[key] = todo
            notifyDataSetChanged()
        }
    }

    fun changeItem(key: String?, todo: Todo?) {
        if (key != null && todo != null) {
            items[key] = todo
            notifyDataSetChanged()
        }
    }

    fun removeItem(key: String?) {
        if (key != null) {
            items.remove(key)
            notifyDataSetChanged()
        }
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
        val item = items.toList()[position]

        holder.textTitle.text = item.second.title
        holder.textDescription.text = item.second.description
        holder.imgRemove.setOnClickListener {
            database.child(item.first).removeValue()
        }

        context.loadingCircle.visibility = View.GONE
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textTitle: TextView = view.textTitle
        val textDescription: TextView = view.textDescription
        val imgRemove: ImageButton = view.imgRemove
    }
}