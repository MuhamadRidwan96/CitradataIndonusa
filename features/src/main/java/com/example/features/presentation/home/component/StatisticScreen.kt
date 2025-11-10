package com.example.features.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Construction
import androidx.compose.material.icons.filled.Domain
import androidx.compose.material.icons.filled.Factory
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        DonutChartScreen()

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2), // 2 kolom
            modifier = modifier
                .fillMaxWidth()
                .heightIn(max = 335.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalItemSpacing = 12.dp,
            contentPadding = PaddingValues(bottom = 12.dp)
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
    }
}


