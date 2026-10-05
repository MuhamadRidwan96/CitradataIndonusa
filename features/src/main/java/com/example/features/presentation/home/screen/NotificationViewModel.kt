package com.example.features.presentation.home.screen

import androidx.lifecycle.viewModelScope
import com.example.core_ui.architecture.action.ActionHandler
import com.example.core_ui.architecture.base.BaseViewModel
import com.example.domain.preferences.UserPreferences
import com.example.domain.usecase.notification.DeleteNotificationUseCase
import com.example.domain.usecase.notification.MarkNotificationReadUseCase
import com.example.domain.usecase.notification.ObserveNotificationUseCase
import com.example.domain.usecase.notification.SyncNotificationUseCase
import com.example.features.presentation.home.state.notification.NotificationUiAction
import com.example.features.presentation.home.state.notification.NotificationUiEvent
import com.example.features.presentation.home.state.notification.NotificationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    userPreferences: UserPreferences,
    observeNotificationUseCase: ObserveNotificationUseCase,
    private val syncNotificationUseCase: SyncNotificationUseCase,
    private val markNotificationReadUseCase: MarkNotificationReadUseCase,
    private val deleteNotificationUseCase: DeleteNotificationUseCase

    ) : BaseViewModel<
        NotificationUiState,
        NotificationUiEvent,
        >
    (initialState = NotificationUiState()),
    ActionHandler<NotificationUiAction> {

    override fun action(action: NotificationUiAction) {

        when (action) {

            is NotificationUiAction.DeleteNotification -> {
                delete(
                    id = action.id,
                    userId = action.userId
                )
            }

            is NotificationUiAction.ClickProject -> {
                openProject(projectId = action.projectId)
            }

            is NotificationUiAction.MarkAsRead -> {
               markAsRead(
                    id = action.id,
                    userId = action.userId
                )
            }

            is NotificationUiAction.Refresh -> {
                viewModelScope.launch {
                    syncNotification()
                }
            }
        }
    }

    private val session = userPreferences.getSession()

    val notification = observeNotificationUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )



    fun markAsRead(id: Int, userId: String) {
        viewModelScope.launch {

            markNotificationReadUseCase(id, userId)
               .onFailure { error ->
                    sendEvent(
                        NotificationUiEvent.Error(
                            error.message ?: "Gagal menandai notifikasi"
                        )
                    )
                }
        }
    }

    fun delete(id: Int, userId: String) {
        viewModelScope.launch {
            deleteNotificationUseCase(id, userId)
               .onFailure { error ->
                    sendEvent(
                        NotificationUiEvent.Error(
                            error.message ?: "Gagal menghapus notifikasi"
                        )
                    )
                }
        }
    }

    fun openProject(projectId: String) {
        if (!currentState.isInitialized) {
            return
        }
        sendEvent(NotificationUiEvent.NavigateToProject(projectId = projectId))
    }


    private suspend fun syncNotification() {

        val currentSession = session.first()
        if (!currentSession.isLogin) return

        syncNotificationUseCase(currentSession.idUser)
            .onSuccess { bundle ->

                reduce {
                    copy(
                        isInitialized = true
                    )
                }

            }.onFailure { error ->
                sendEvent(NotificationUiEvent.Error(error.message ?: "Gagal sync notifikasi"))
            }
    }
}


