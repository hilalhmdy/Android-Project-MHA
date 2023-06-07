package com.example.mygithubuser.ui.follow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mygithubuser.api.ApiConfig
import com.example.mygithubuser.model.FollowResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel: ViewModel() {

    private val _userData = MutableLiveData<ArrayList<FollowResponse>>()
    val userData : LiveData<ArrayList<FollowResponse>> = _userData

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUserFollowers(username: String?){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowers(username)
        client.enqueue(object : Callback<ArrayList<FollowResponse>> {

            override fun onResponse(
                call: Call<ArrayList<FollowResponse>>,
                response: Response<ArrayList<FollowResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val followUser = response.body()
                    if (followUser != null) {
                        _userData.value = followUser!!
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<FollowResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "FollowFragment"
    }

}