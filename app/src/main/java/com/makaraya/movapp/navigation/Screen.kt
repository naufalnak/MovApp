package com.makaraya.movapp.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.makaraya.movapp.R

sealed class Screen(
    val route: String,
    val subRoute: String?,
    @StringRes val title: Int?,
    val icon: ImageVector?
) {
    companion object {
        val navScreenList = listOf(Movies, Favorites, Profile)
    }

    object Welcome : Screen(
        route = "welcome_screen",
        null,
        null,
        null
    )
    object Login : Screen(
        route = "login",
        null,
        null,
        null

    )
    object Register : Screen(
        route = "register",
        null,
        null,
        null
    )
    object Movies : Screen(
        "MoviesScreen",
        "MoviesDiscoverScreen",
        R.string.nav_title_movies,
        Icons.Default.PlayArrow
    )

    object MoviesDiscover : Screen(
        "MoviesDiscoverScreen",
        null,
        null,
        null
    )

    object MoviesDetails : Screen(
        "MovieDetailsScreen/{movieId}",
        null,
        null,
        null
    )

    object Favorites : Screen(
        "FavoriteMoviesScreen",
        "FavoriteDiscoverScreen",
        R.string.nav_title_favorites,
        Icons.Default.Star
    )

    object FavoritesDiscover : Screen(
        "FavoriteDiscoverScreen",
        null,
        null,
        null
    )

    object FavoritesDetails : Screen(
        "FavoriteDetailsScreen/{movieId}",
        null,
        null,
        null
    )

    object Profile : Screen(
        "UserProfileScreen",
        null,
        R.string.nav_title_profile,
        Icons.Default.Person
    )
}