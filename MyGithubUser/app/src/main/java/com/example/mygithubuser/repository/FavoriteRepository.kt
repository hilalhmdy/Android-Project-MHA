package com.example.mygithubuser.repository

import android.app.Application
import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import com.example.mygithubuser.database.Favorite
import com.example.mygithubuser.database.FavoriteDAO
import com.example.mygithubuser.database.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application ) {

    private val mFavoriteDAO: FavoriteDAO

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDAO = db.favoriteDao()
    }

    fun getAllFavorites(): LiveData<List<Favorite>> = mFavoriteDAO.getAllFavorite()

    fun insert(favorite: Favorite) {
        executorService.execute { mFavoriteDAO.insert(favorite) }
    }

    fun delete(favorite: Favorite) {
        executorService.execute { mFavoriteDAO.delete(favorite) }
    }

    fun getFavoriteUser(username: String): LiveData<Favorite?> = mFavoriteDAO.getFavorite(username)

    fun deleteFavoriteByUsername(username : String) = mFavoriteDAO.deleteFavoriteByUsername(username)

}