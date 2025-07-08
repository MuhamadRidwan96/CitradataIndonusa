package com.example.features.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val icon: ImageVector, val title: String) {
    data object Home : BottomNavItem("home", Icons.Outlined.Home, "Home")
    data object Search : BottomNavItem("search", Icons.Outlined.Explore, "Explore")
    data object Favorite : BottomNavItem("favorite", Icons.Outlined.Favorite, "Favorite")
    data object Profile : BottomNavItem("profile", Icons.Outlined.Person, "Profile")

}