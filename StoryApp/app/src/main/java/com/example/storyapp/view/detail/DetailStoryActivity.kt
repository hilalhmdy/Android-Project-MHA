package com.example.storyapp.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.storyapp.databinding.ActivityDetailStoryBinding
import com.example.storyapp.model.response.ListStoryItem

class DetailStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<ListStoryItem>("DATA")
        binding.usernameDetailStory.text = data?.name
        binding.descriptionDetailStory.text = data?.description
        Glide.with(this)
            .load(data?.photoUrl)
            .apply(RequestOptions().centerCrop())
            .into(binding.photoDetailStory)
    }
}