package com.example.domain.repository

import com.example.domain.model.NotificationModel
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {

    fun observeNotifications(): Flow<List<NotificationModel>>

    fun observeUnreadCount(): Flow<Int>

    suspend fun sync(userId: String): Result<Unit>

    suspend fun markAsRead(id: Int, userId: String): Result<Unit>

    suspend fun delete(id: Int, userId: String): Result<Unit>
}