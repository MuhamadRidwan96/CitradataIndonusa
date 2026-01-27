package com.example.features.nav

import androidx.annotation.DrawableRes

data class BottomNavItem(
    val destination: String,
    val title: String,
    @DrawableRes val icon: Int
)

