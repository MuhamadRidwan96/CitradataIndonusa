package com.example.features.nav.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.features.presentation.authentication.screen.login.ScreenLogin
import com.example.features.presentation.authentication.screen.signup.SignUpScreen

fun NavGraphBuilder.authNavGraph(navController: NavHostController){
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthNavigationScreen.Login.route
    ){



        composable(AuthNavigationScreen.Login.route){
           ScreenLogin(
               onLoginSuccess = {
                   navController.navigate(Graph.HOME){
                       popUpTo(Graph.AUTHENTICATION){inclusive = true}
                       launchSingleTop = true
                   }
               },
               onSignUpClick = {navController.navigate(AuthNavigationScreen.SignUp.route)}
           )
        }

        composable(AuthNavigationScreen.SignUp.route){
            SignUpScreen(
                onBackClick = { navController.navigate(AuthNavigationScreen.Login.route) }
            )
        }
    }
}


sealed class AuthNavigationScreen(val route :String){
    data object SignUp : AuthNavigationScreen(route = "SIGNUP")
    data object Login : AuthNavigationScreen(route = "LOGIN")
}