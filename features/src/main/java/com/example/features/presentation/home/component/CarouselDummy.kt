package com.example.features.presentation.home.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.core_ui.R
import com.example.core_ui.component.Carousel
import com.example.core_ui.component.CarouselItem

@Composable
fun CarouselDummy(modifier: Modifier = Modifier) {

    val dummyItems = remember {
        listOf(
            CarouselItem(
                imageRes = R.drawable.carousel_001,
                status = "Baru",
                date = "3 Mei 2025",
                title = "Industrial",
                location = "Jakarta",
                category = "Konstruksi"
            ),
            CarouselItem(
                imageRes = R.drawable.carousel_002,
                status = "Sedang Berjalan",
                date = "1 Mei 2025",
                title = "Infrastruktur",
                location = "Bandung",
                category = "Infrastruktur"
            ),
            CarouselItem(
                imageRes = R.drawable.carousel_003,
                status = "Sedang Berjalan",
                date = "1 Mei 2025",
                title = "Konstruksi",
                location = "Bandung",
                category = "Infrastruktur"
            )
        )
    }

        // Limit carousel height to prevent excessive rendering
        Carousel(items = dummyItems)
}
