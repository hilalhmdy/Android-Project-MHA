package com.example.mygithubuser.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mygithubuser.api.ApiConfig
import com.example.mygithubuser.database.Favorite
import com.example.mygithubuser.model.UserDetailResponse
import com.example.mygithubuser.repository.FavoriteRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application): ViewModel() {

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun insert(favorite: Favorite) {
        mFavoriteRepository.insert(favorite)
    }

    private val _isFavorite = MutableLiveData<UserDetailResponse>()
    val favorite : LiveData<UserDetailResponse> = _isFavorite

    fun deleteByUsername(username: String) {
        mFavoriteRepository.deleteFavoriteByUsername(username)
    }

    fun delete(favorite: Favorite){
        mFavoriteRepository.delete(favorite)
    }

    fun getFavorite(username: String) : LiveData<Favorite?> {
        return mFavoriteRepository.getFavoriteUser(username)
    }

    private val _userData = MutableLiveData<UserDetailResponse>()
    val userData : LiveData<UserDetailResponse> = _userData

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun getDetailUser(username: String?){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<UserDetailResponse> {

            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val detailUser = response.body()
                    if (detailUser != null) {
                        _userData.value = detailUser!!
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
    companion object {
        private const val TAG = "DetailViewModel"
    }
}