package com.makaraya.movapp.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen(route = "welcome_screen")
    object Login : Screen(route = "login")
    object Home : Screen(route = "home_screen")
}