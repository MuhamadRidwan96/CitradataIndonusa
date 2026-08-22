package com.example.features.presentation.home.state.dashboard

import androidx.compose.runtime.Immutable
import com.example.core_ui.architecture.base.BaseUiState
import com.example.domain.model.DonutData
import com.example.domain.model.StatisticProvince
import com.example.domain.model.TrendProject
import com.example.domain.model.UserProfile
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf

@Immutable
data class DashboardUiState(
    val isLoading: Boolean = false,
    val isLoaded : Boolean = false,
    val isRefresh: Boolean = false,
    val isInitialized: Boolean = false,

    val totalProjects: Int = 0,

    val isShowAll : Boolean = false,

    val error: String? = null,
    val user: UserProfile? = null,
    val byCategory: ImmutableMap<String, Int> = persistentMapOf(),
    val byStatus: ImmutableList<DonutData> = persistentListOf(),
    val byProvince: ImmutableList<StatisticProvince> = persistentListOf(),
    val categoryTrend: ImmutableMap<String, Int> = persistentMapOf(),
    val dashboard: ImmutableList<TrendProject> = persistentListOf(),
    val visibleProvince: ImmutableList<StatisticProvince> = persistentListOf(),

    ) : BaseUiState
