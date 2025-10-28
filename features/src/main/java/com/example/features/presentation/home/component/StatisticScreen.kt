package com.example.features.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Construction
import androidx.compose.material.icons.filled.Domain
import androidx.compose.material.icons.filled.Factory
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.features.presentation.home.screen.StatisticViewModel

@Composable
fun StatisticScreen(
    modifier: Modifier = Modifier,
    viewModel: StatisticViewModel = hiltViewModel(),

    ) {
    val state by viewModel.statisticState.collectAsStateWithLifecycle()


    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("By status", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.SemiBold)

        DonutChartScreen()

        Text("By categories", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.SemiBold)

        LazyRow(
            modifier = modifier
                .fillMaxWidth()
                .height(160.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(state.byCategory.entries.toList()) { (category, count) ->

                val trend = state.categoryTrends[category] ?: 0

                StatisticCard(
                    title = category,
                    count = count,
                    icon = when (category) {
                        "HRC" -> Icons.Default.Domain
                        "MDL" -> Icons.Default.Apartment
                        "IND" -> Icons.Default.Factory
                        "LOW" -> Icons.Default.Construction
                        else -> Icons.Default.Info
                    },
                    showTrend = true,
                    trendValue = trend
                )
            }
        }
        Text(
            text = "By Provinces",
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold
        )

        ProvinceChart(
            provinceData = state.byProvince
        )
    }
}


