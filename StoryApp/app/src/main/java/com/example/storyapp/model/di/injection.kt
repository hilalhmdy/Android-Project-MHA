package com.example.storyapp.model.di

import android.content.Context
import com.example.storyapp.model.paging.StoryRepository
import com.example.storyapp.model.service.ApiConfig

object Injection {


    fun provideRepository(context: Context): StoryRepository {
//        val user = getUser(pref)
//        val token = user.token
        val apiService = ApiConfig.getApiService(context)
        return StoryRepository.getInstance(context, apiService)
    }
}