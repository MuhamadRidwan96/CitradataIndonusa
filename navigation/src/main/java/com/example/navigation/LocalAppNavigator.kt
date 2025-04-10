package com.example.navigation

import androidx.compose.runtime.staticCompositionLocalOf

val LocalAppNavigator = staticCompositionLocalOf<AppNavigator> {
    error("AppNavigator not provided")
}