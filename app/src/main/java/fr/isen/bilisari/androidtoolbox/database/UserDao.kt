package fr.isen.bilisari.androidtoolbox.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.isen.bilisari.androidtoolbox.model.User

@Dao
interface UserDao {

    @Query("SELECT * from users ORDER BY surname ASC, firstname ASC")
    fun getAlphabetizedUsers(): LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Query("DELETE FROM users")
    suspend fun deleteAll()
}