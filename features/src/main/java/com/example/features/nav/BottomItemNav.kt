package com.example.features.nav

import androidx.annotation.DrawableRes
import com.example.core_ui.R

sealed class BottomNavItem(val route: String, @DrawableRes val icon: Int, val title: String) {
    data object Home : BottomNavItem("home", R.drawable.house, "Home")
    data object Search : BottomNavItem("search", R.drawable.compass, "Explore")
    data object Favorite : BottomNavItem("favorite", R.drawable.folder_heart, "Favorite")
    data object Profile : BottomNavItem("profile", R.drawable.hard_hat, "Profile")

}