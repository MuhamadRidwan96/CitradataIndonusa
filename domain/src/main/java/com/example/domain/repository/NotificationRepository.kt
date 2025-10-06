package com.example.domain.repository

import kotlinx.coroutines.flow.StateFlow

interface NotificationRepository {
    val notificationCount: StateFlow<Int>
    fun increaseCount()
    fun resetCount()
}