package fr.isen.bilisari.androidtoolbox.activity.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import fr.isen.bilisari.androidtoolbox.database.UserRoomDatabase
import fr.isen.bilisari.androidtoolbox.model.User
import kotlinx.coroutines.coroutineScope

class UserRoomViewModel(application: Application) : AndroidViewModel(application) {

    // LiveData gives us updated words when they change.
    val allUsers: LiveData<List<User>>
    val usersDao = UserRoomDatabase.getDatabase(application).userDao()

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
        // the correct WordRepository.
        allUsers = usersDao.getAlphabetizedUsers()
    }

    /**
     * The implementation of insert() in the database is completely hidden from the UI.
     * Room ensures that you're not doing any long running operations on
     * the main thread, blocking the UI, so we don't need to handle changing Dispatchers.
     * ViewModels have a coroutine scope based on their lifecycle called
     * viewModelScope which we can use here.
     */
    suspend fun insert(user: User) = coroutineScope {
        usersDao.insert(user)
    }
}