package com.example.domain.repository

import com.example.domain.model.NotificationModel
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun getAllNotifications(): Flow<List<NotificationModel>>
    fun getUnreadCount(): Int
    suspend fun insert(notification: NotificationModel)
    suspend fun markAllAsRead(id:Int)
    suspend fun delete(id: Int)
}