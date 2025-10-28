package com.example.core_ui.component

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.delay

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
                delay(autoScrollDelay)
                val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
                pagerState.animateScrollToPage(
                    page = nextPage,
                    animationSpec = tween(durationMillis = 500, easing = LinearEasing)
                )
            }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp)// ✅ Reduced from 140.dp to 100.dp for smaller size
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth(),
                //.aspectRatio(20f / 9f), // ✅ More compact aspect ratio (2.22:1)
            pageSpacing = 8.dp,
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) { page ->
            CarouselItemView(
                item = items[page]
            )
        }

        DotsIndicator(
            pagerState = pagerState,
            count = items.size,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun CarouselItemView(item: CarouselItem) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            //.aspectRatio(20f / 9f) // ✅ Consistent with carousel aspect ratio
            .clip(RoundedCornerShape(8.dp)) // ✅ Smaller radius for compact look
           // .clickable { item.onClick?.invoke() }
    ) {
        // Background Image
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(item.imageRes)
                .crossfade(true)
                .build(),
            contentDescription = "Carousel Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        // Gradient Overlay for better text readability
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.7f)
                        ),
                        startY = 0.3f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
        )

        // Content with better layout
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp)
        ) {
            // Title
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            // Location and Category
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 2.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    tint = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.size(12.dp)
                )
                Text(
                    text = item.location,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.8f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 2.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    imageVector = Icons.Default.Category,
                    contentDescription = "Category",
                    tint = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.size(12.dp)
                )
                Text(
                    text = item.category,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.8f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 2.dp)
                )
            }

            // Date
            Text(
                text = item.date,
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.6f),
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}

@Composable
fun DotsIndicator(
    pagerState: PagerState,
    count: Int,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        repeat(count) { index ->
            val isSelected = index == pagerState.currentPage
            val transition = updateTransition(targetState = isSelected, label = "dotTransition")

            val width by transition.animateDp(
                transitionSpec = { tween(durationMillis = 200) },
                label = "dotWidth"
            ) { selected ->
                if (selected) 8.dp else 4.dp // ✅ Smaller dots for compact design
            }

            val color by transition.animateColor(
                transitionSpec = { tween(durationMillis = 200) },
                label = "dotColor"
            ) { selected ->
                if (selected) Color.White else Color.White.copy(alpha = 0.5f)
            }

            Box(
                modifier = Modifier
                    .padding(horizontal = 2.dp)
                    .size(width, 3.dp) // ✅ Reduced height
                    .clip(CircleShape) // ✅ Always circular for cleaner look
                    .background(color)
            )
        }
    }
}

