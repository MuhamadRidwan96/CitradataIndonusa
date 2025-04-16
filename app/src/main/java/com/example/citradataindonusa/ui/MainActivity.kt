package com.example.citradataindonusa.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.navigation.LocalAppNavigator
import com.example.navigation.MainNavigation
import com.example.navigation.Routes
import com.example.core_ui.CitraDataIndonusaTheme
import com.example.feature_authentication.presentation.screen.login.ScreenLogin
import com.example.feature_authentication.presentation.screen.signup.SignUpScreen
import com.example.feature_authentication.presentation.screen.SplashScreen
import com.example.feature_home.ScreenHome
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this)
        setContent {
            CitraDataIndonusaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val appNavigator = remember { AppNavigatorImpl(navController) }
                    val startDestination = Routes.SPLASH

                    CompositionLocalProvider(LocalAppNavigator provides appNavigator) {
                        CitraDataIndonusaTheme {
                            MainNavigation(
                                navController = navController,
                                startDestination = startDestination,
                                splashScreen = {
                                    SplashScreen { isLoggedIn ->
                                        if (isLoggedIn) {
                                            appNavigator.navigateToHome()
                                        } else {
                                            appNavigator.navigateToLogin()
                                        }
                                    }
                                },
                                screenHome = { ScreenHome() },
                                screenLogin = { ScreenLogin() },
                                screenSignUp = { SignUpScreen() }
                            )
                        }
                    }
                }
            }
        }
    }
}
