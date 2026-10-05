package com.example.features.presentation.home.state.notification

import com.example.core_ui.architecture.base.BaseUiAction

sealed interface NotificationUiAction: BaseUiAction {

    data object Refresh : NotificationUiAction

    data class DeleteNotification(
        val id: Int,
        val userId: String
    ) : NotificationUiAction

    data class MarkAsRead(
        val id: Int,
        val userId: String
    ) : NotificationUiAction

    data class ClickProject(
        val projectId: String
    ) : NotificationUiAction
}