package com.example.features.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.domain.model.DonutData
import com.example.features.presentation.home.state.StatisticsDataState
import kotlinx.collections.immutable.ImmutableList

@Composable
fun StatisticScreen(
    modifier: Modifier = Modifier,
    statistic: StatisticsDataState,
    status: ImmutableList<DonutData>

) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        DonutChartScreen(status = status)

        Spacer(modifier = Modifier.height(10.dp))

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2), // 2 kolom
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 335.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalItemSpacing = 12.dp,
            contentPadding = PaddingValues(bottom = 12.dp)
        ) {
            items(statistic.byCategory.entries.toList(), key = { it.key }) { (category, count) ->

                val trend = statistic.categoryTrends[category] ?: 0
                val categoryIcons = remember {
                    mapOf(
                        "HRC" to Icons.Default.Domain,
                        "MDL" to Icons.Default.Apartment,
                        "IND" to Icons.Default.Factory,
                        "LOW" to Icons.Default.Construction
                    )
                }

                StatisticCard(
                    title = category,
                    count = count,
                    icon = categoryIcons[category]?: Icons.Default.Info,
                    showTrend = true,
                    trendValue = trend
                )
            }
        }
    }
}


