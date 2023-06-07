package com.example.storyapp.view.login

import androidx.lifecycle.ViewModel
import com.example.storyapp.model.paging.StoryRepository

class LoginViewModel(private val storyRepository: StoryRepository) : ViewModel() {

    fun loginAPI(email : String, password: String) =  storyRepository.loginAPI(email, password)

}