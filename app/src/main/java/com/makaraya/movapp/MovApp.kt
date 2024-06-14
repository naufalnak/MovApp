package com.makaraya.movapp

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.makaraya.movapp.navigation.BottomNavBar
import com.makaraya.movapp.navigation.NavGraph
import com.makaraya.movapp.navigation.Screen
import com.makaraya.movapp.presentation.screen.splash.SplashViewModel
import com.makaraya.movapp.ui.theme.MovAppTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MovApp(splashViewModel: SplashViewModel) {
    MovAppTheme {
        val navController = rememberAnimatedNavController()
        val startDestination by splashViewModel.startDestination

        LaunchedEffect(startDestination) {
            navController.navigate(startDestination) {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
            }
        }

        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry.value?.destination?.route

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            bottomBar = {
                if (currentDestination in listOf(Screen.Movies.route, Screen.Favorites.route, Screen.Profile.route)) {
                    BottomNavBar(navController)
                }
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                NavGraph(navController, startDestination)
            }
        }
    }
}
