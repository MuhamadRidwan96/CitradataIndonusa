package com.example.features.presentation.home.component

import android.content.res.Configuration
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.filled.Business
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core_ui.AppTheme
import kotlin.math.abs


@Composable
fun StatisticCard(
    modifier: Modifier = Modifier,
    title: String,
    count: Int,
    icon: ImageVector? = null,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    accentColor: Color = MaterialTheme.colorScheme.primary,
    showTrend: Boolean = false,
    trendValue: Int = 0
) {
    val animatedCount by animateIntAsState(
        targetValue = count,
        animationSpec = tween(
            durationMillis = 800,
            easing = FastOutSlowInEasing
        ),
        label = "countAnimation"
    )

    Card(
        modifier = modifier
            .height(140.dp)
            .width(100.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            // Icon section
            if (icon != null) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(32.dp)
                        .background(
                            color = accentColor.copy(alpha = 0.15f),
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = accentColor,
                        modifier = Modifier.size(16.dp)
                    )
                }
            } else {
                Spacer(modifier = Modifier.height(6.dp))
            }

            // Count section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = animatedCount.toString(),
                    color = textColor,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium,
                )

                Text("project",style = MaterialTheme.typography.bodySmall, color = Color.Gray)

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
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Title section
            Text(
                text = title.uppercase(),
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center,
                color = textColor.copy(alpha = 0.8f),
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.8.sp,
                lineHeight = 12.sp
            )
        }
    }
}


@Preview(showBackground = true, name = "light")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "dark theme"
)
@Composable
fun Preview1(){
    AppTheme {
        StatisticCard(
            title = "HRC",
            count = 450,
            icon = Icons.Default.Business,
            backgroundColor = MaterialTheme.colorScheme.surface,
            textColor = MaterialTheme.colorScheme.onSurface,
            showTrend = true,
            trendValue = 45
        )
    }
}

