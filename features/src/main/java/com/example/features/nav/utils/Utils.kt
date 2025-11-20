package com.example.features.nav.utils

import com.example.features.nav.BottomNavItem

/**
 * Determines whether the bottom bar should be shown for the given route.
 *
 * @param route The current navigation route to check
 * @return true if bottom bar should be visible, false otherwise
 */

fun shouldShowBottomBar(route: String?): Boolean {
    return when (route) {
        BottomNavItem.Home.route,
        BottomNavItem.Search.route,
        BottomNavItem.Favorite.route,
        BottomNavItem.Profile.route -> true
        else -> false
    }
}