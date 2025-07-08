package com.example.features.nav.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.features.presentation.authentication.screen.login.SplashScreen

fun NavGraphBuilder.splashNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.SPLASH,
        startDestination = SplashNavigationScreen.Splash.route
    ) {
        composable(SplashNavigationScreen.Splash.route){
            SplashScreen(
                onNavigateToHome = {
                    navController.navigate(Graph.HOME) {
                        popUpTo(Graph.SPLASH) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onNavigateToLogin = {
                    navController.navigate(Graph.AUTHENTICATION) {
                        popUpTo(Graph.SPLASH) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

sealed class SplashNavigationScreen(val route: String) {
    data object Splash : SplashNavigationScreen(route = "SPLASH")
}