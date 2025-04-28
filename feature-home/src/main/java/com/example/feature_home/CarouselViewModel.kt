package com.example.feature_home

import androidx.lifecycle.ViewModel
import com.example.domain.model.CarouselItem

class CarouselViewModel : ViewModel() {
    val carouselItems = listOf(

        CarouselItem(
            "Completed", "May 28, 2024", "Heritage Building Restoration",
            "Old Town, Hokkaido", "Restoration",
            "https://source.unsplash.com/800x600/?building,restoration"
        ),
        CarouselItem(
            "Ongoing", "June 10, 2024", "City Park Renovation",
            "Tokyo, Japan", "Construction",
            "https://source.unsplash.com/800x600/?park,renovation"
        ),
        CarouselItem(
            "Planned", "July 5, 2024", "New Library Project",
            "Osaka, Japan", "Education",
            "https://source.unsplash.com/800x600/?library,construction"
        )
    )
}