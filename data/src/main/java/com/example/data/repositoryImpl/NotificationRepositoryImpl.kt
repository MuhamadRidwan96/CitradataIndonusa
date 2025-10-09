package com.example.data.repositoryImpl

import com.example.data.local.dao.NotificationDao
import com.example.data.local.toDomain
import com.example.data.local.toEntity
import com.example.domain.model.NotificationModel
import com.example.domain.repository.NotificationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(private val dao: NotificationDao) :
    NotificationRepository {
    override fun getAllNotifications(): Flow<List<NotificationModel>> {
        return dao.getAllNotifications().map { list -> list.map { it.toDomain() } }
    }

    override fun getUnreadCount(): Flow<Int> {
        return dao.getUnreadCount()
    }

    override suspend fun insert(notification: NotificationModel) {
        withContext(Dispatchers.IO) {
            dao.insert(notification.toEntity())
        }
    }

    override suspend fun markAllAsRead(id:Int) {
        dao.markAllAsRead(id)
    }

    override suspend fun delete(id: Int) {
        dao.deleteNotification(id)
    }
}