package com.example.movie.data

import com.example.movie.model.FakeMovieDataSource
import com.example.movie.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MovieRepository {

    private val movieList = mutableListOf<Movie>()

    init {
        if (movieList.isEmpty()) {
            FakeMovieDataSource.dummyMovie.forEach {
                movieList.add(it)
            }
        }
    }

    fun getIPhoneList(): Flow<List<Movie>> {
        return flowOf(movieList)
    }

    fun getIPhoneById(id: Long): Movie {
        return movieList.first {
            it.id == id
        }
    }

    companion object {
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(): MovieRepository =
            instance ?: synchronized(this) {
                MovieRepository().apply {
                    instance = this
                }
            }
    }
}