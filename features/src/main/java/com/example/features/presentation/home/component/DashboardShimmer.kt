package com.example.features.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core_ui.AppTheme
import com.valentinilk.shimmer.shimmer

@Composable
fun DashboardShimmer(
    modifier: Modifier = Modifier
) {
    val baseColor = Color(0xFF2C2C2C)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .shimmer(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        Spacer(modifier = Modifier.height(4.dp))

        Row(horizontalArrangement = Arrangement.SpaceBetween) {

            Box(
                modifier = Modifier
                    .width(175.dp)
                    .height(30.dp)
                    .background(
                        baseColor,
                        RoundedCornerShape(12.dp)
                    )
            )
        }

        Box(
            modifier = Modifier
                .width(250.dp)
                .height(14.dp)
                .background(
                    baseColor,
                    RoundedCornerShape(12.dp)
                )
        )

        Spacer(modifier = Modifier.height(4.dp))

        //Statistic Card
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Box(
                modifier = Modifier
                    .width(125.dp)
                    .height(170.dp)
                    .background(
                        baseColor,
                        RoundedCornerShape(16.dp)
                    )
            )
            Box(
                modifier = Modifier
                    .width(125.dp)
                    .height(170.dp)
                    .background(
                        baseColor,
                        RoundedCornerShape(16.dp)
                    )
            )
            Box(
                modifier = Modifier
                    .width(125.dp)
                    .height(170.dp)
                    .background(
                        baseColor,
                        RoundedCornerShape(16.dp)
                    )
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        // Line Chart Statistic
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp)
                .background(
                    baseColor,
                    RoundedCornerShape(16.dp)
                )
        )

        // Donut Chart

        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp)
                .background(
                    baseColor,
                    RoundedCornerShape(16.dp)
                )
        )


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(80.dp)
                    .background(baseColor, RoundedCornerShape(12.dp))
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun Preview() {
    AppTheme {
        DashboardShimmer()
    }
}