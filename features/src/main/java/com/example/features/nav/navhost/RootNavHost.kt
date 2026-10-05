package com.example.features.nav.navhost

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.core_ui.component.NoInternetBottomSheet
import com.example.core_ui.component.TokenExpiredBottomSheet
import com.example.data.network.NetworkEvent
import com.example.data.utils.NetworkEventManager
import com.example.features.nav.destination.Screen
import com.example.features.presentation.MainScreen
import com.example.features.presentation.authentication.screen.login.ScreenLogin
import com.example.features.presentation.authentication.screen.login.SplashScreen
import com.example.features.presentation.authentication.screen.signup.SignUpScreen


@Suppress("EffectKeys")
@Composable
fun RootNavHost(
    modifier: Modifier = Modifier,
    networkEventManager: NetworkEventManager
) {
    val navController = rememberNavController()

    val networkEvent by networkEventManager.events.collectAsStateWithLifecycle()

    Box(
        modifier = modifier.fillMaxSize()
    ) {

        // ============================================================
        // Navigation
        // ============================================================

        NavHost(navController = navController, startDestination = Screen.Splash.route) {

            /** ---------------- Splash ---------------- */

            composable(Screen.Splash.route) {

                SplashScreen(
                    onCheckLogin = { isLoggedIn ->

                        if (isLoggedIn) {

                            navController.navigate(
                                Screen.Main.route
                            ) {
                                popUpTo(Screen.Splash.route) {
                                    inclusive = true
                                }
                            }

                        } else {

                            navController.navigate(
                                Screen.Login.route
                            ) {
                                popUpTo(Screen.Splash.route) {
                                    inclusive = true
                                }
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

                            navController.navigate(
                                Screen.Main.route
                            ) {
                                popUpTo("auth") {
                                    inclusive = true
                                }

                                launchSingleTop = true
                            }
                        },

                        onSignUpClick = {
                            navController.navigate(
                                Screen.SignUp.route
                            )
                        }
                    )
                }

                composable(Screen.SignUp.route) {

                    SignUpScreen(

                        onBackClick = {
                            navController.popBackStack()
                        },

                        onSignInClick = {
                            navController.navigate(
                                Screen.Login.route
                            )
                        }
                    )
                }
            }

            /** ---------------- Main ---------------- */

            composable(Screen.Main.route) {

                MainScreen(
                    onLogout = {

                        navController.navigate(
                            Screen.Login.route
                        ) {
                            popUpTo(Screen.Main.route) {
                                inclusive = true
                            }

                            launchSingleTop = true
                        }
                    }
                )
            }
        }

        // ============================================================
        // Global Network Error
        //
        // IMPORTANT:
        // Diletakkan SETELAH NavHost supaya berada di atas navigation.
        // ============================================================

        // Retry akan kita hubungkan
        // ke feature yang mengalami error.
        if (networkEvent == NetworkEvent.TokenExpired) {

            TokenExpiredBottomSheet(
                onLogin = {

                    networkEventManager.clearEvents()

                    navController.navigate(
                        Screen.Login.route
                    ) {

                        popUpTo(0) {
                            inclusive = true
                        }

                        launchSingleTop = true
                    }
                }
            )
        } else if (networkEvent == NetworkEvent.NoInternet) {

            NoInternetBottomSheet(
                onRetry = {

                    networkEventManager.clearEvents()

                    // Retry akan kita hubungkan
                    // ke feature yang mengalami error.
                }
            )
        } else if (false) Unit
    }
}