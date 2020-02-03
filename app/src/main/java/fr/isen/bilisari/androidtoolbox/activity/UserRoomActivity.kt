package fr.isen.bilisari.androidtoolbox.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.bilisari.androidtoolbox.R
import fr.isen.bilisari.androidtoolbox.activity.adapter.UserRoomAdapter
import fr.isen.bilisari.androidtoolbox.activity.viewmodel.UserRoomViewModel
import fr.isen.bilisari.androidtoolbox.model.User
import kotlinx.android.synthetic.main.activity_user_room.*

class UserRoomActivity : AppCompatActivity() {

    private lateinit var wordViewModel: UserRoomViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_room)

        val adapter = UserRoomAdapter(this)
        viewUsersRoom.adapter = adapter
        viewUsersRoom.layoutManager = LinearLayoutManager(this)

        wordViewModel = ViewModelProvider(this).get(UserRoomViewModel::class.java)
        wordViewModel.allUsers.observe(this, Observer<List<User>> { users ->
            // Update the cached copy of the users in the adapter
            users?.let { adapter.setUsers(users) }
        })
    }
}