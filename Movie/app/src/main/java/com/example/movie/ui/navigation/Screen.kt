package com.example.movie.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object About : Screen("about")
    object DetailMovie : Screen("home/{id}") {
        fun createRoute(id: Long) = "home/$id"
    }
}
