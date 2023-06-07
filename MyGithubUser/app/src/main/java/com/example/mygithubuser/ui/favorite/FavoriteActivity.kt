package com.example.mygithubuser.ui.favorite

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithubuser.R
import com.example.mygithubuser.adapter.FavoriteAdapter
import com.example.mygithubuser.adapter.UserAdapter
import com.example.mygithubuser.api.ApiConfig
import com.example.mygithubuser.database.Favorite
import com.example.mygithubuser.databinding.ActivityFavoriteBinding
import com.example.mygithubuser.databinding.ActivityMainBinding
import com.example.mygithubuser.helper.ViewModelFactory
import com.example.mygithubuser.model.ItemsItem
import com.example.mygithubuser.model.UserGithubResponse
import com.example.mygithubuser.repository.FavoriteRepository
import com.example.mygithubuser.ui.main.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

//    fun getAllFavorites(): LiveData<List<Favorite>> = mFavoriteRepository.getAllFavorites()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorite User"

        val layoutManager = LinearLayoutManager(this)
        binding.rvlistFavoriteUser.layoutManager = layoutManager
        binding.rvlistFavoriteUser.setHasFixedSize(true)

//        getUser("sidiqpermana")

        val favoriteViewModel = obtainViewModel(this@FavoriteActivity)

//        favoriteViewModel.delete(Favorite(0, "hilal", "https://picsum.photos/200"))

        showLoading(true)
        
        favoriteViewModel.getAllFavorites().observe(this, { favoriteList ->
            if (favoriteList != null) {
                showLoading(false)
                val adapter = FavoriteAdapter(favoriteList)
                binding.rvlistFavoriteUser.adapter = adapter
            }
        })

    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        private const val TAG = "FavoritActivity"

    }

}