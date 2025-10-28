package com.example.features.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core_ui.AppTheme
import com.example.core_ui.component.PieChart
import com.example.core_ui.component.PieData

@Composable
fun PieChartScreen() {
    val pie = listOf(
        PieData("HRC", 341f, Color(0xFFE74C3C)),
        PieData("MDL", 285f, Color(0xFF27AE60)),
        PieData("IND", 109f, Color(0xFFF1C40F)),
        PieData("LOW", 4f, Color(0xFF1ABC9C))

    )

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        PieChart(pie)

        Spacer(modifier = Modifier.height(16.dp))

        pie.forEach { item ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(item.color, CircleShape)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    "${item.label} : ${item.value.toInt()}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChartPie(){
    AppTheme {
        PieChartScreen()
    }
}