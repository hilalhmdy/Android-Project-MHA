package com.example.storyapp.view.register

import androidx.lifecycle.ViewModel
import com.example.storyapp.model.paging.StoryRepository

class RegisterViewModel(private val storyRepository: StoryRepository) : ViewModel() {

    fun registerAPI(username: String, email: String, password: String) = storyRepository.registerAPI(username, email, password)

}