package com.example.features.presentation.home.state

data class StatisticsDataState(
    val totalProjects: Int = 0,
    val byCategory: Map<String, Int> = emptyMap(),
    val byStatus: Map<String, Int> = emptyMap(),
    val byProvince: Map<String, Int> = emptyMap(),
    val categoryTrends: Map<String, Int> = emptyMap(),
    val isLoading: Boolean = false,
    val error : String? =  null
)