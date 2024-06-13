package com.makaraya.movapp

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.makaraya.movapp.navigation.Screen
import com.makaraya.movapp.screen.home.HomeScreen
import com.makaraya.movapp.screen.welcome.WelcomeScreen

@OptIn(ExperimentalAnimationApi::class, ExperimentalPagerApi::class)
@ExperimentalMaterial3Api
@Composable
fun MovApp(
    navController: NavHostController,
    startDestination: String
) {
    Scaffold(
        topBar = {

        },
        bottomBar = {

        },
        modifier = Modifier,
        content = { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(route = Screen.Welcome.route) {
                    WelcomeScreen(navController = navController)
                }
                composable(route = Screen.Home.route) {
                    HomeScreen(navController = navController)
                }
                // Define other screens here if necessary
            }
        }
    )
}