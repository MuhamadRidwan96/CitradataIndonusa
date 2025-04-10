package com.example.citradataindonusa.ui

import androidx.navigation.NavHostController
import com.example.navigation.Routes
import com.example.navigation.AppNavigator

class AppNavigatorImpl(
    private val navController: NavHostController
) : AppNavigator {
    override fun navigateToHome() {
        navController.navigate(Routes.HOME) {
            popUpTo(Routes.LOGIN) { inclusive = true }
            launchSingleTop = true
        }
    }

    override fun navigateToLogin() {
        navController.navigate(Routes.LOGIN){
            popUpTo(Routes.SPLASH){inclusive = true}
            launchSingleTop = true
        }
    }

    override fun navigateToSignUp() {
        navController.navigate(Routes.SIGN_UP) {
            popUpTo(Routes.LOGIN) { inclusive = false}
        }
    }

    override fun navigateBackToLogin() {
        navController.navigate(Routes.LOGIN){
            popUpTo(Routes.HOME) {inclusive = true}
            launchSingleTop = true
        }
    }

}