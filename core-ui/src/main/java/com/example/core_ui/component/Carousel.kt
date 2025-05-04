package com.example.core_ui.component

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import coil.compose.AsyncImage
import com.example.core_ui.R
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Carousel(
    items: List<CarouselItem>,
    modifier: Modifier = Modifier,
    autoScrollDelay: Long = 6000L
) {
    val pagerState = rememberPagerState(pageCount = { items.size })

    // Auto-scroll dengan snapshotFlow untuk menghindari memory leak
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }
            .collect {
                delay(autoScrollDelay) // Delay antar perpindahan halaman
                val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
                pagerState.animateScrollToPage(
                    page = nextPage,
                    animationSpec = tween(durationMillis = 500, easing = LinearEasing)
                )
            }
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
            pageSpacing = 6.dp, // Menambahkan space antar halaman
            contentPadding = PaddingValues(horizontal = 16.dp) // Membuat efek margin di kiri & kanan
        ) { page ->
            CarouselItemView(
                item = items[page]
            )
        }

        // Dots Indicator berada di luar HorizontalPager agar tidak ikut bergeser
        DotsIndicator(
            pagerState = pagerState,
            count = items.size,
            modifier = Modifier.padding(vertical = 16.dp)
        )
    }
}

@Composable
fun CarouselItemView(item: CarouselItem) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2f)
            .clip(RoundedCornerShape(16.dp))
    ) {
        // Gambar Background
        AsyncImage(
            model = item.imageUrl,
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        // Overlay Gelap untuk Kontras dengan Teks
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Black.copy(alpha = 0.5f))
        )

        // Konten Teks
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            // Row untuk Status dan Tanggal
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Status Text
                Text(
                    text = item.status,
                    fontSize = 12.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                // Date Text
                Text(
                    text = item.date,
                    fontSize = 12.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Title Text
            Text(
                text = item.title,
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Location and Category Rows
            IconTextRow(iconResId = R.drawable.ic_location, text = item.location)
            Spacer(modifier = Modifier.height(4.dp))
            IconTextRow(iconResId = R.drawable.ic_category, text = item.category)
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DotsIndicator(pagerState: PagerState, count: Int, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        repeat(count) { index ->
            val isSelected = index == pagerState.currentPage
            val transition = updateTransition(targetState = isSelected, label = "dotTransition")

            // Animasi untuk lebar dan warna
            val width by transition.animateDp(
                transitionSpec = { tween(durationMillis = 300) },
                label = "dotWidth"
            ) { selected ->
                if (selected) 15.dp else 8.dp // Lebar berubah saat aktif
            }

            val color by transition.animateColor(
                transitionSpec = { tween(durationMillis = 300) },
                label = "dotColor"
            ) { selected ->
                if (selected) Color.White else Color.Gray
            }

            // Animasi untuk radius (menggunakan animateFloat untuk lebih halus)
            val cornerRadiusFraction by transition.animateFloat(
                transitionSpec = { tween(durationMillis = 300) },
                label = "cornerRadius"
            ) { selected ->
                if (selected) 1f else 0.5f // Fraction dari radius
            }

            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(width, 8.dp) // Tinggi tetap sama
                    .clip(RoundedCornerShape(cornerRadiusFraction * 8.dp)) // Radius dinamis
                    .background(color)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFECECEC)
@Composable
fun Prev2() {
    val dummyItems = listOf(
        CarouselItem(
            imageUrl = "https://picsum.photos/800/400?random=1",
            status = "Baru",
            date = "3 Mei 2025",
            title = "Proyek Jalan Tol",
            location = "Jakarta",
            category = "Konstruksi"
        ),
        CarouselItem(
            imageUrl = "https://picsum.photos/800/400?random=2",
            status = "Sedang Berjalan",
            date = "1 Mei 2025",
            title = "Jembatan Baru",
            location = "Bandung",
            category = "Infrastruktur"
        )
    )

    Carousel(items = dummyItems)
}