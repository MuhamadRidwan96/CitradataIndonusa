package com.example.features.presentation.home.state.notification

import androidx.compose.runtime.Immutable
import com.example.core_ui.architecture.base.BaseUiState
import com.example.domain.model.NotificationModel

@Immutable
data class NotificationUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val notifications: List<NotificationModel> = emptyList(),
    val isInitialized: Boolean = false
) : BaseUiState