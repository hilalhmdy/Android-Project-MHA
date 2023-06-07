package com.example.storyapp.view.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyapp.R
import com.example.storyapp.databinding.ActivityMainBinding
import com.example.storyapp.model.data.UserModel
import com.example.storyapp.model.data.UserPreference
import com.example.storyapp.view.adapter.LoadingStateAdapter
import com.example.storyapp.view.adapter.StoryListAdapter
import com.example.storyapp.view.helper.ViewModelFactory
import com.example.storyapp.view.login.LoginActivity
import com.example.storyapp.view.maps.MapsActivity
import com.example.storyapp.view.story.AddStoryActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    var sharedpreferences: SharedPreferences? = null
    val mypreference = "mypref"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progresbar.visibility = View.GONE
        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        setupViewModel()

    }

    private fun getLogin(context: Context): Boolean{
        val user : UserModel
        runBlocking {
            user = UserPreference.getInstance(context.dataStore).getUser().first()
        }
        return user.isLogin
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(this)
        )[MainViewModel::class.java]

        binding.storiesRV.layoutManager = LinearLayoutManager(this)
        val adapter = StoryListAdapter(this)
        binding.storiesRV.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        mainViewModel.story.observe(this) {
            if (getLogin(this)) {
                adapter.submitData(lifecycle, it)
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progresbar.visibility = if (isLoading) View.VISIBLE else View.GONE

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu1 -> {
                val cameraIntent = Intent(this, AddStoryActivity::class.java)
                startActivity(cameraIntent)
                return true
            }
            R.id.maps -> {
                val mapsIntent = Intent(this, MapsActivity::class.java)
                startActivity(mapsIntent)
                return true
            }
            R.id.menu2 -> {
                logout()
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
                return true
            }
        }
        return false
    }

    private fun logout(){
        lifecycleScope.launch {
            UserPreference.getInstance(dataStore).logout()
        }
    }
}
