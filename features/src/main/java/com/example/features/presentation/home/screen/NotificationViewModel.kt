package com.example.features.presentation.home.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.notification.DeleteNotificationUseCase
import com.example.domain.usecase.notification.GetAllNotificationUseCase
import com.example.domain.usecase.notification.GetNotificationCountUseCase
import com.example.domain.usecase.notification.ResetNotificationCountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val getNotificationCountUseCase: GetNotificationCountUseCase,
    getAllNotificationUseCase: GetAllNotificationUseCase,
    private val resetNotificationCountUseCase: ResetNotificationCountUseCase,
    private val deleteNotificationUseCase: DeleteNotificationUseCase
) : ViewModel() {

    private val _unreadCount = MutableStateFlow(0)
    val unreadCount = _unreadCount.asStateFlow()

    init {
        fetchNotification()
    }

    val notification = getAllNotificationUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),emptyList())

    fun fetchNotification(){
        viewModelScope.launch {
            _unreadCount.value = getNotificationCountUseCase()
        }
    }

    fun markAllAsRead(id: Int){
        viewModelScope.launch {
            resetNotificationCountUseCase(id)
        }
    }

    fun delete(id:Int){
        viewModelScope.launch {
        deleteNotificationUseCase(id)
        }
    }
}
