package com.example.core_ui.component

import androidx.annotation.DrawableRes


data class CarouselItem(
val status: String,
val date: String,
val title: String,
val location: String,
val category: String,
@DrawableRes val imageRes: Int
)