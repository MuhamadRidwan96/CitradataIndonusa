package com.example.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun MainNavigation(
    navController: NavHostController,
    startDestination: String,
    screenLogin: @Composable () -> Unit,
    screenHome: @Composable () -> Unit,
    screenSignUp: @Composable () -> Unit,
    splashScreen: @Composable () -> Unit
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Routes.SPLASH) {splashScreen() }
        composable(Routes.LOGIN) { screenLogin() }
        composable(Routes.HOME) { screenHome() }
        composable(Routes.SIGN_UP) { screenSignUp() }
    }
}