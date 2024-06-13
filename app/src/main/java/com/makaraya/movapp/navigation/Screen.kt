package com.makaraya.movapp.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen(route = "welcome_screen")
    object Login : Screen(route = "login")
    object Register : Screen(route = "register")
    object Home : Screen(route = "home_screen")
}