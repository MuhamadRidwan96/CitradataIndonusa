package com.example.features.presentation.home.state.dashboard

import com.example.core_ui.architecture.base.BaseUiAction

sealed interface DashboardUiAction : BaseUiAction{

    data class OnSearchQueryChanged(
        val query: String
    ) : DashboardUiAction

    data class OnCategoryClick(
        val category: String
    ) : DashboardUiAction

    data class OnStatusClick(
        val status: String
    ) : DashboardUiAction

    data class OnCityClick(
        val city: String
    ) : DashboardUiAction

    data object OnNotificationClick : DashboardUiAction

    data object OnToggleProvince : DashboardUiAction

    data object OnLogoutClicked : DashboardUiAction

    data object OnRefresh : DashboardUiAction

    data object OnRetry : DashboardUiAction


}