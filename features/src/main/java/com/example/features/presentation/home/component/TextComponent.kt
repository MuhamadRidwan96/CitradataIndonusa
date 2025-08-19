package com.example.features.presentation.home.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Factory
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.House
import androidx.compose.material.icons.filled.OtherHouses
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.core_ui.component.Carousel
import com.example.core_ui.component.CarouselItem
import com.example.core_ui.component.FilterCategory
import com.example.core_ui.component.FilterCategoryRow
import com.example.core_ui.component.IconBackground
import com.example.feature_login.R
import com.example.features.presentation.home.utils.formatToRupiah

@Composable
fun TitleSection(
    @DrawableRes icon: Int,
    title: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp, top = 12.dp)
    ) {
        IconBackground(icon)
        Text(
            text = title,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun InfoItem(title: String, content: @Composable () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            text = title,
            color = Color.Gray,
            style = MaterialTheme.typography.labelSmall
        )
        content()
    }
}

@Composable
fun CurrencyText(price: String?) {
    Text(
        text = formatToRupiah(price),
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onSurface,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun TextMain(text: String) {

    Text(
        text = text,
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Bold
    )
}



/*
@Composable
private fun CarouselSection() {

    val dummyItems = remember {
        listOf(
            CarouselItem(
                imageRes = R.drawable.carousel_001,
                status = "Baru",
                date = "3 Mei 2025",
                title = "Proyek Jalan Tol",
                location = "Jakarta",
                category = "Konstruksi"
            ),
            CarouselItem(
                imageRes = R.drawable.carousel_002,
                status = "Sedang Berjalan",
                date = "1 Mei 2025",
                title = "Jembatan Baru",
                location = "Bandung",
                category = "Infrastruktur"
            ),
            CarouselItem(
                imageRes = R.drawable.carousel_003,
                status = "Sedang Berjalan",
                date = "1 Mei 2025",
                title = "Jembatan Baru",
                location = "Bandung",
                category = "Infrastruktur"
            )
        )
    }

    // Limit carousel height to prevent excessive rendering
    Carousel(
        items = dummyItems,
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp) // Fixed height for better performance
    )
}
*/


