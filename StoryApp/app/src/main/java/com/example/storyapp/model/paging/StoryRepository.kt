package com.example.storyapp.model.paging

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.storyapp.R
import com.example.storyapp.model.data.UserModel
import com.example.storyapp.model.data.UserPreference
import com.example.storyapp.model.response.ListStoryItem
import com.example.storyapp.model.response.LoginResponse
import com.example.storyapp.model.response.RegisterResponse
import com.example.storyapp.model.response.StoriesResponse
import com.example.storyapp.model.response.UploadStoriesResponse
import com.example.storyapp.model.service.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class StoryRepository(private val context: Context, private val apiService: ApiService) {

    private val loginResult = MediatorLiveData<Result<String>>()
    private val registerResult = MediatorLiveData<Result<String>>()
    private val uploadResult = MediatorLiveData<Result<String>>()
    private val mapsResult = MediatorLiveData<Result<List<ListStoryItem>>>()

    fun getAllStories():LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService)
            }
        ).liveData
    }

    fun loginAPI(email : String, password : String): LiveData<Result<String>> {
        loginResult.value = Result.Loading
        val client = apiService.loginUser(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        CoroutineScope(Dispatchers.IO).launch {
                            UserPreference.getInstance(context.dataStore).loginPref(responseBody.loginResult?.name.toString(), email, password, responseBody.loginResult?.token.toString())
                        }
                    }
                    loginResult.value = Result.Success("Success")
                }
                else {
                    loginResult.value = Result.Error("Failed")
                    }
                }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                loginResult.value = Result.Error(t.message.toString())
            }
        })
        return loginResult
    }

    fun registerAPI(username: String, email: String, password: String) : LiveData<Result<String>>{
        registerResult.value = Result.Loading
        val client = apiService.registerUser(username, email, password)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        registerResult.value = Result.Success("Success")
                    }
                }
                else {
                    registerResult.value = Result.Error("Failed")
                }
            }
            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                registerResult.value = Result.Error(t.message.toString())
            }
        })
        return registerResult
    }

    fun uploadImage(file: MultipartBody.Part,  description : RequestBody) : LiveData<Result<String>>{
        uploadResult.value = Result.Loading
        val client = apiService.uploadImage(file, description)
        client.enqueue(object : Callback<UploadStoriesResponse> {
            override fun onResponse(
                call: Call<UploadStoriesResponse>,
                response: Response<UploadStoriesResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error!!) {
                        uploadResult.value = Result.Success("Success")
                    }
                } else {
                    uploadResult.value = Result.Error("Failed")
                }
            }
            override fun onFailure(call: Call<UploadStoriesResponse>, t: Throwable) {
                uploadResult.value = Result.Error(t.message.toString())
            }
        })
        return uploadResult
    }

    fun mapsAPI(): LiveData<Result<List<ListStoryItem>>>{
        mapsResult.value = Result.Loading
        val client = apiService.getMapAllStories()
        client.enqueue(object : Callback<StoriesResponse> {
            override fun onResponse(
                call: Call<StoriesResponse>,
                response: Response<StoriesResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        mapsResult.value = Result.Success(responseBody.listStory)
                    }
                } else {
                    mapsResult.value = Result.Error("Failed")
                }
            }
            override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
                mapsResult.value = Result.Error(t.message.toString())
            }
        })
        return mapsResult
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: StoryRepository? = null
        fun getInstance(

            context: Context,
            apiService: ApiService
        ): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(context, apiService)
            }.also { instance = it }
    }
}

