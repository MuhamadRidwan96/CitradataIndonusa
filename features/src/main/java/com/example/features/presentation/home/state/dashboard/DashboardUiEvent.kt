package com.example.features.presentation.home.state.dashboard

import com.example.core_ui.architecture.base.BaseUiEvent

sealed interface DashboardUiEvent : BaseUiEvent {

    data class Error(
        val message: String
    ) : DashboardUiEvent

    data class SnackBar(
        val message: String
    ) : DashboardUiEvent

    data class NavigateToProjectByCategory(
        val cat: String
    ) : DashboardUiEvent

    data class NavigateToProjectByStatus(
        val status: String
    ) : DashboardUiEvent

    data class NavigateToProjectByCity(
        val city: String
    ) : DashboardUiEvent

    data object NavigateToNotification : DashboardUiEvent

    data object Logout : DashboardUiEvent
}