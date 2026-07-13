package com.example.features.presentation.home.state

import androidx.compose.runtime.Immutable
import com.example.domain.model.DonutData
import com.example.domain.model.StatisticProvince
import com.example.domain.model.TrendProject
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class StatisticsDataState(
    val totalProjects: Int = 0,
    val byCategory: Map<String, Int> = emptyMap(),
    val byStatus: ImmutableList<DonutData> = persistentListOf(),
    val byProvince: ImmutableList<StatisticProvince> = persistentListOf(),
    val categoryTrend : Map<String, Int> = emptyMap(),
    val dashboard: ImmutableList<TrendProject> = persistentListOf(),
    val isLoading: Boolean = false,
    val isLoaded : Boolean = false,
    val isShowAll : Boolean = false,
    val error : String? =  null
)