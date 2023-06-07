package com.example.storyapp.view.maps

import androidx.lifecycle.ViewModel
import com.example.storyapp.model.paging.StoryRepository

class MapsViewModel(private val storyRepository: StoryRepository) : ViewModel() {

    fun mapsAPI() = storyRepository.mapsAPI()
}