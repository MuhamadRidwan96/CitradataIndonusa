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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core_ui.component.DonutChart
import com.example.domain.model.DonutData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun DonutChartScreen(
    modifier: Modifier = Modifier,
    status: ImmutableList<DonutData>
) {

    Card(
        modifier = modifier.fillMaxSize()
          /*  .border(
                width = 0.5.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(20.dp)
            )*/,
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(20.dp),


        ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Project Status Overview",
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.titleSmall

            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                DonutChart(data = status)

                Spacer(modifier = Modifier.size(10.dp))

                Legend(
                    chartData = status
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun Legend(
    chartData: ImmutableList<DonutData>,
    modifier: Modifier = Modifier
) {

    val total = chartData.sumOf { it.value.toDouble() }
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        chartData.forEach { item ->

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(item.color, shape = CircleShape)

                )

                Text(
                    text = item.label,
                    style = MaterialTheme.typography.labelSmallEmphasized,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "${item.value.toInt()} (${(item.value / total * 100).format(1)}%)",
                    style = MaterialTheme.typography.labelSmallEmphasized,
                    color = MaterialTheme.colorScheme.onSurface
                )

            }
        }
    }
}

fun Double.format(digits: Int) =
    "%.${digits}f".format(this)

@Preview(showBackground = true, name = "Donut Chart - Light Theme")
@Composable
private fun PreviewDonutChartScreen() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val sampleData = persistentListOf(
                DonutData(
                    label = "Completed",
                    value = 45f,
                    color = Color(0xFF4CAF50)
                ),
                DonutData(
                    label = "In Progress",
                    value = 30f,
                    color = Color(0xFF2196F3)
                ),
                DonutData(
                    label = "Pending",
                    value = 15f,
                    color = Color(0xFFFF9800)
                ),
                DonutData(
                    label = "Cancelled",
                    value = 10f,
                    color = Color(0xFFF44336)
                )
            )

            DonutChartScreen(
                modifier = Modifier.padding(16.dp),
                status = sampleData
            )
        }
    }
}

