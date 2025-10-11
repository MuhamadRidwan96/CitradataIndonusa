package com.example.features.nav.utils

import com.example.features.nav.BottomNavItem

fun shouldShowBottomBar(route: String?): Boolean {
    return when (route) {
        BottomNavItem.Home.route,
        BottomNavItem.Search.route,
        BottomNavItem.Favorite.route,
        BottomNavItem.Profile.route -> true
        else -> false
    }
}