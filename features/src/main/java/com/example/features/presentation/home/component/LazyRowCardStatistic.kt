package com.example.features.presentation.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core_ui.R
import com.example.core_ui.component.TextTitle
import com.example.features.presentation.home.state.StatisticsDataState

@Composable
fun LazyRowCardStatistic(modifier: Modifier = Modifier, statistic: StatisticsDataState) {

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextTitle(
            title = stringResource(R.string.statistic),
            desc = "Real-time project monitoring"
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 2.dp)
        ) {
            items(
                items = statistic.byCategory.entries.toList(),
                key = { it.key }
            ) { (category, count) ->

                val trend = statistic.categoryTrend[category] ?: 0

                val categoryIcons = remember {
                    mapOf(
                        "HRC" to R.drawable.building,
                        "MDL" to R.drawable.building_2,
                        "IND" to R.drawable.factory,
                        "LOW" to R.drawable.armchair
                    )
                }

                StatisticCard(
                    title = category,
                    count = count,
                    image = categoryIcons[category] ?: R.drawable.hard_hat,
                    showTrend = true,
                    trendValue = trend
                )
            }
        }
    }
}