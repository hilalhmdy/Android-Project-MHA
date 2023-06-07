package com.example.movie.model

data class Movie(
    val id: Long,
    val name: String,
    val description: String,
    val image: Int,
    val date: String,
    val actor: String
)