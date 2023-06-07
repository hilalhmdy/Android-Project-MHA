package com.example.mygithubuser.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mygithubuser.api.ApiConfig
import com.example.mygithubuser.model.UserGithubResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    private val _listUser = MutableLiveData<UserGithubResponse>()
    val listUser : LiveData<UserGithubResponse> = _listUser

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isFindData = MutableLiveData<Boolean>(true)
    val isFindData: LiveData<Boolean> = _isFindData

    fun getUser(query: String?) {
        _isLoading.value = true
        _isFindData.value = false
        var inputSearch = query?.replace(" ", "")
        val client = ApiConfig.getApiService().getUser(inputSearch)
        client.enqueue(object : Callback<UserGithubResponse> {

            override fun onResponse(
                call: Call<UserGithubResponse>,
                response: Response<UserGithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val listUser = response.body()
                    if (listUser != null) {
                        _listUser.value = listUser!!
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserGithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }


    companion object {
        private const val TAG = "MainActivity"

    }
}