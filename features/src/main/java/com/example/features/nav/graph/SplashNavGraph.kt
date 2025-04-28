package com.example.features.nav.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.features.presentation.authentication.screen.SplashScreen

fun NavGraphBuilder.splashNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.SPLASH,
        startDestination = SplashNavigationScreen.Splash.route
    ) {
        composable(SplashNavigationScreen.Splash.route){
            SplashScreen(
                onNavigate = {isLoggedIn ->
                    navController.popBackStack()
                    if (isLoggedIn){
                        navController.navigate(Graph.HOME)
                    } else {
                        navController.navigate(Graph.AUTHENTICATION)
                    }
                }
            )
        }
    }
}

sealed class SplashNavigationScreen(val route: String) {
    data object Splash : SplashNavigationScreen(route = "SPLASH")
}