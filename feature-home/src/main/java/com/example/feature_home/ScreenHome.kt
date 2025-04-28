package com.example.feature_home

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.core_ui.component.SearchBar
import com.example.domain.model.CarouselItem
import kotlinx.coroutines.delay

@Composable
fun ScreenHome(
    modifier: Modifier = Modifier,
    viewModel: LogOutViewModel = hiltViewModel()
) {


    var query by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        SearchBar(
            query = query,
            onQueryChange = { query = it }
        )
        Text(
            text = stringResource(id = R.string.Greeting),
            modifier = modifier.padding(horizontal = 8.dp), // Give space from Divider
            color = Color.Gray
        )

        Button(onClick = {
            viewModel.logout()
        }) {
            Text(stringResource(R.string.Logout))
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CarouselScreen(viewModel: CarouselViewModel = viewModel()) {
    val pagerState = rememberPagerState(pageCount = { viewModel.carouselItems.size })

    // Auto-scroll dengan snapshotFlow untuk menghindari memory leak
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }
            .collect {
                delay(6000) // Delay antar perpindahan halaman
                val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
                pagerState.animateScrollToPage(
                    page = nextPage,
                    animationSpec = tween(durationMillis = 500, easing = LinearEasing)
                )
            }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
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
                item = viewModel.carouselItems[page]
            )
        }

        // Dots Indicator berada di luar HorizontalPager agar tidak ikut bergeser
        DotsIndicator(
            pagerState = pagerState,
            count = viewModel.carouselItems.size,
            modifier = Modifier
                .padding(vertical = 16.dp)
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
    CarouselScreen()
}

@Composable
fun IconTextRow(
    iconResId: Int,
    text: String,
    iconTint: Color = Color.Black,
    textColor: Color = Color.Black
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(iconResId),
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            fontSize = 12.sp,
            color = textColor
        )
    }
}

@Composable
fun ProjectCard(
    title: String,
    date: String,
    category: String,
    location: String,
    status: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Title and Status
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black, // Pastikan warna teks terlihat
                    modifier = Modifier.weight(1f)
                )
                StatusIndicator(status)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Date and Category
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconTextRow(iconResId = R.drawable.ic_calendar, text = date)
                IconTextRow(iconResId = R.drawable.ic_category, text = category)
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Location
            IconTextRow(iconResId = R.drawable.ic_location, text = location)
        }
    }
}

@Composable
fun StatusIndicator(status: String) {
    val statusColor = when (status) {
        "Active" -> Color.Green
        "Inactive" -> Color.Gray
        else -> Color.Gray // Default color for unknown status
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(statusColor)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = status,
            color = Color.Black,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFECECEC)
@Composable
fun PreviewProjectCard() {
    ProjectCard(
        title = "Harbor Tower Development",
        date = "Jun 15, 2023",
        category = "Construction",
        location = "Downtown, Tokyo",
        status = "Active"
    )
}
