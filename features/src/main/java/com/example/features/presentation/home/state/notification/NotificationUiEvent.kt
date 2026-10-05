package com.example.features.presentation.home.state.notification

import com.example.core_ui.architecture.base.BaseUiEvent

sealed interface NotificationUiEvent : BaseUiEvent {

    data class Error(val message : String) : NotificationUiEvent

    data class SnackBar(val message: String) : NotificationUiEvent

    data class NavigateToProject(val projectId: String) : NotificationUiEvent

}