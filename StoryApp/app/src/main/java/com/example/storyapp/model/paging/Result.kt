package com.example.storyapp.model.paging

sealed class Result<out R> private constructor(){
    data class Success<out T>(val data: T) : Result<T>()
    data class Error( val Error: String) : Result<Nothing>()
    object Loading : Result<Nothing>()
}