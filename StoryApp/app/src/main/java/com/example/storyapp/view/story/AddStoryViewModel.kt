package com.example.storyapp.view.story

import androidx.lifecycle.ViewModel
import com.example.storyapp.model.paging.StoryRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddStoryViewModel(private val storyRepository: StoryRepository) : ViewModel() {

    fun uploadImage(file: MultipartBody.Part, description : RequestBody) = storyRepository.uploadImage(file, description)
}