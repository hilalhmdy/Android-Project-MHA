package com.example.mygithubuser.database

import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: Favorite)

    @Query("DELETE FROM favorite WHERE nameuser= :username")
    fun deleteFavoriteByUsername(username:String)

    @Delete
    fun delete(favorite: Favorite)

    @Query("SELECT * from favorite WHERE nameuser= :username limit 1")
    fun getFavorite(username: String): LiveData<Favorite?>

    @Query("SELECT * from favorite ORDER BY id ASC")
    fun getAllFavorite(): LiveData<List<Favorite>>
}