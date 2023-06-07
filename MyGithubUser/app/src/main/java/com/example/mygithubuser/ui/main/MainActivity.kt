package com.example.mygithubuser.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithubuser.adapter.UserAdapter
import com.example.mygithubuser.databinding.ActivityMainBinding
import com.example.mygithubuser.model.ItemsItem
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.mygithubuser.R
import com.example.mygithubuser.ui.favorite.FavoriteActivity
import com.example.mygithubuser.ui.setting.*

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvlistUser.layoutManager = layoutManager
        binding.rvlistUser.setHasFixedSize(true)

        supportActionBar?.title = "Github User's Search"

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        mainViewModel.isLoading.observe(this, {
            showLoading(it)
        })

        mainViewModel.listUser.observe(this, {  listuser->
            setUserList(listuser.items)

        })
        mainViewModel.isFindData.observe(this, {
            letsFindData(it)
        })

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView: SearchView = findViewById(R.id.idSV)
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        binding.idSV.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchView.clearFocus()
                mainViewModel.getUser(query)

                return false;
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false;
            }
        })

        val pref = SettingPreferences.getInstance(dataStore)
        val menuSettingViewModel = ViewModelProvider(this, ViewMenuModelFactory(pref)).get(
            MenuSettingViewModel::class.java
        )
        menuSettingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

    }

    private fun setUserList(items: List<ItemsItem>) {
        val listReview : List<ItemsItem> = items

        if(listReview.isEmpty()){
            Toast.makeText(this, "Username not found", Toast.LENGTH_LONG).show()
        }
        val adapter = UserAdapter(listReview)
        binding.rvlistUser.adapter = adapter

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun letsFindData(isLoading: Boolean) {
        binding.messegeFind.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu1 -> {
                val moveIntent = Intent(this@MainActivity, MenuSettingActivity::class.java)
                startActivity(moveIntent)
            }
            R.id.menu2 -> {
                val moveIntent = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(moveIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }


}