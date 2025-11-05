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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core_ui.AppTheme
import com.valentinilk.shimmer.shimmer

@Composable
fun DashboardShimmer() {
    val baseColor = Color(0xFF2C2C2C)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .shimmer(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Spacer(modifier = Modifier.height(92.dp))
        //SearchBar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(35.dp)
                .background(
                    baseColor,
                    RoundedCornerShape(12.dp)
                )
        )

        //Image carousel
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(145.dp)
                .background(
                    baseColor,
                    RoundedCornerShape(16.dp)
                )
        )

        Spacer(modifier = Modifier.height(6.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .height(25.dp)
                .background(
                    baseColor,
                    RoundedCornerShape(8.dp)
                )
        )

        Spacer(modifier = Modifier.height(4.dp))
        //Statistic
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .background(
                    baseColor,
                    RoundedCornerShape(16.dp)
                )
        )
        Spacer(modifier = Modifier.height(6.dp))



        repeat(2) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(2) {
                    Box(
                        modifier = Modifier
                            .height(170.dp)
                            .width(180.dp)
                            .background(baseColor, RoundedCornerShape(12.dp))
                    )
                }
            }
        }


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

@Preview(showBackground = true)
@Composable
fun PreviewShimmer() {
    AppTheme {
        DashboardShimmer()
    }
}