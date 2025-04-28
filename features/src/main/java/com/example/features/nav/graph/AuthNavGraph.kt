package com.example.features.nav.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.features.presentation.authentication.screen.ScreenLogin
import com.example.features.presentation.authentication.screen.SignUpScreen

fun NavGraphBuilder.authNavGraph(navController: NavHostController){
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthNavigationScreen.Login.route
    ){

        composable(AuthNavigationScreen.Login.route){
           ScreenLogin(
               onLoginSuccess = {
                   navController.popBackStack()
                   navController.navigate(Graph.HOME)
               },
               onSignUpClick = {navController.navigate(AuthNavigationScreen.SignUp.route)}
           )
        }

        composable(AuthNavigationScreen.SignUp.route){
            SignUpScreen(
                onBackClick = {navController.popBackStack()}
            )
        }
    }
}

sealed class AuthNavigationScreen(val route :String){
    data object SignUp : AuthNavigationScreen(route = "SIGNUP")
    data object Login : AuthNavigationScreen(route = "LOGIN")
}