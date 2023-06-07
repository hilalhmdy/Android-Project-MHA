package com.example.mygithubuser.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mygithubuser.ui.setting.MenuSettingActivity
import com.example.mygithubuser.R
import com.example.mygithubuser.adapter.SectionsPagerAdapter
import com.example.mygithubuser.database.Favorite
import com.example.mygithubuser.databinding.ActivityDetailUserBinding
import com.example.mygithubuser.helper.ViewModelFactory
import com.example.mygithubuser.model.*
import com.example.mygithubuser.ui.favorite.FavoriteActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayout

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var data: Favorite
    private lateinit var detailViewModel : DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.elevation = 0f
        supportActionBar?.title = "Github Detail User's"

        detailViewModel = obtainViewModel(this@DetailUserActivity)

        detailViewModel.isLoading.observe(this, {
            showLoading(it)
        })

        detailViewModel.userData.observe(this, { userData->
            setDetailUser(userData)
        })

        data = intent.getParcelableExtra(EXTRA_USER)!!

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = data.nameUser.toString()

        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tab_layout)

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        detailViewModel.getDetailUser(data.nameUser)

        detailViewModel.getFavorite(data.nameUser.toString()).observe(this, {favoriteUser->
            favoriteUser?.let {
                binding.addFab.setImageResource(R.drawable.ic_favorit);
                binding.addFab.setOnClickListener { view ->
                    Toast.makeText(this, "Favorite User Deleted", Toast.LENGTH_LONG).show()
                    detailViewModel.delete(favoriteUser)
                }
            } ?: run{
                binding.addFab.setImageResource(R.drawable.ic_favorit_border);
                val favorite = Favorite()
                favorite.nameUser = data.nameUser
                favorite.avatars = data.avatars
                binding.addFab.setOnClickListener { view ->
                    Toast.makeText(this, "Favorite User Added", Toast.LENGTH_LONG).show()
                    detailViewModel.insert(favorite)
                }
            }
        }
        )
    }

    private fun setDetailUser(items: UserDetailResponse?){
        binding.apply {
            tvItemName.text = items?.name
            tvUsername.text = items?.username
            tvFollowersLabel.text = items?.followers.toString()
            tvFollowingLabel.text = items?.following.toString()
            Glide.with(this@DetailUserActivity)
                .load(items?.avatarUrl)
                .apply(RequestOptions().centerCrop())
                .into(profileImage)
        }
//        binding.tvItemName.text = items?.name
//        binding.tvUsername.text = items?.username
//        binding.tvFollowersLabel.text = items?.followers.toString()
//        binding.tvFollowingLabel.text = items?.following.toString()
//        Glide.with(this@DetailUserActivity)
//            .load(items?.avatarUrl)
//            .apply(RequestOptions().centerCrop())
//            .into(binding.profileImage)

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DetailViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu1 -> {
                val moveIntent = Intent(this@DetailUserActivity, MenuSettingActivity::class.java)
                startActivity(moveIntent)
            }
            R.id.menu2 -> {
                val moveIntent = Intent(this@DetailUserActivity, FavoriteActivity::class.java)
                startActivity(moveIntent)
            }

        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        const val EXTRA_USER = "extra_user"
    }

}