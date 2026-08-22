package com.example.features.nav.navhost

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.features.nav.destination.Screen
import com.example.features.presentation.MainScreen
import com.example.features.presentation.authentication.screen.login.ScreenLogin
import com.example.features.presentation.authentication.screen.login.SplashScreen
import com.example.features.presentation.authentication.screen.signup.SignUpScreen


@Composable
fun RootNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        /** ---------------- Splash ---------------- */
        composable(Screen.Splash.route) {
            SplashScreen(

                onCheckLogin = { isLoggedIn ->
                    if (isLoggedIn) {
                        navController.navigate(Screen.Main.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    } else {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    }
                }
            )
        }
        /** ---------------- Auth ---------------- */
        navigation(
            startDestination = Screen.Login.route,
            route = "auth"
        ) {

            composable(Screen.Login.route) {
                ScreenLogin(
                    onLoginSuccess = {
                        navController.navigate(Screen.Main.route) {
                            popUpTo("auth") { inclusive = true }
                        }
                    },
                    onSignUpClick = {
                        navController.navigate(Screen.SignUp.route)
                    }
                )
            }

            composable(Screen.SignUp.route) {
                SignUpScreen(
                    onBackClick = { navController.popBackStack() },
                    onSignInClick = {
                        navController.navigate(Screen.Login.route)
                    },
                )
            }
        }


        /** ---------------- Main ---------------- */
        composable(Screen.Main.route) {
            MainScreen(
                onLogout = {

                    navController.navigate(Screen.Login.route){
                        popUpTo(Screen.Main.route){ inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
