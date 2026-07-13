package com.example.features.presentation.home.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core_ui.R
import kotlin.math.abs


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun StatisticCard(
    modifier: Modifier = Modifier,
    title: String,
    count: Int,
    @DrawableRes image: Int,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    showTrend: Boolean = false,
    trendValue: Int = 0
) {


    Card(
        modifier = modifier
            .height(170.dp)
            .width(125.dp)
        /*    .border(
                width = 0.5.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(20.dp)
            )*/,
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(20.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            // Icon section
            if (true) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(42.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                            shape = CircleShape
                        )
                ) {
                    Image(
                        painter = painterResource(image),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            } else {
                Spacer(modifier = Modifier.height(6.dp))
            }

            // Count section

            Text(
                text = count.toString(),
                color = textColor,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge,
            )

            Text(
                "project",
                style = MaterialTheme.typography.labelSmallEmphasized,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Trend indicator (optional)
            if (showTrend && trendValue != 0) {
                val trendIcon =
                    if (trendValue > 0) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward
                val trendColor =
                    if (trendValue > 0) Color(0xFF4CAF50) else Color(0xFFF44336)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 2.dp)
                ) {
                    Icon(
                        imageVector = trendIcon,
                        contentDescription = "Trend",
                        tint = trendColor,
                        modifier = Modifier.size(12.dp)
                    )
                    Text(
                        text = "${abs(trendValue)}%",
                        color = trendColor,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Title section
            Text(
                text = title.uppercase(),
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center,
                color = textColor.copy(alpha = 0.8f),
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.8.sp,
                lineHeight = 12.sp
            )
        }
    }
}

// Preview dengan berbagai variasi
@Preview(name = "Light Mode", showBackground = true)
@Composable
private fun StatisticCardPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.background
                        )
                    )
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Basic card without icon
            StatisticCard(
                title = "Total Users",
                count = 1234,
                image = R.drawable.building,
            )

            // Card with icon
            StatisticCard(
                title = "Total Sales",
                count = 8942,
                image = R.drawable.building_2
            )

            // Card with positive trend
            StatisticCard(
                title = "Active Users",
                count = 5678,
                image = R.drawable.factory,
                showTrend = true,
                trendValue = 15
            )

            // Card with negative trend
            StatisticCard(
                title = "Bounce Rate",
                count = 32,
                image = R.drawable.armchair,
                backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
                showTrend = true,
                trendValue = -5
            )
        }
    }
}

