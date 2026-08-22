package com.example.data.repositoryImpl

import com.example.data.local.dao.NotificationDao
import com.example.data.local.entity.NotificationEntity
import com.example.data.local.toDomainNotification
import com.example.data.local.toEntityNotification
import com.example.data.network.api.ApiHelper
import com.example.domain.model.NotificationModel
import com.example.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val dao: NotificationDao
) : NotificationRepository {
    override fun observeNotifications(): Flow<List<NotificationModel>> {
        return dao.observeNotifications()
            .map { entities ->
                entities.map(NotificationEntity::toDomainNotification)
            }
        Timber.tag("CEK REPO").d("${dao.observeNotifications()}")
    }

    override fun observeUnreadCount(): Flow<Int> {
        return dao.observeUnreadCount()
    }

    override suspend fun sync(userId: String): Result<Unit> {
        return runCatching {
           val response = apiHelper.notificationList(userId)
            dao.sync(
                response.data.map{
                    it.toEntityNotification()
                }
            )
        }
    }

    override suspend fun markAsRead(
        id: Int,
        userId: String
    ): Result<Unit> {
        return runCatching {
            val response = apiHelper.readNotification(
                id = id,
                userId = userId
            )
            check(response.isSuccessful)
            dao.markAsRead(id)

        }
    }

    override suspend fun delete(id: Int, userId: String): Result<Unit> {
        return runCatching {
           val response =  apiHelper.deleteNotification(
                id = id,
                userId = userId
            )
            check(response.isSuccessful)
            dao.deleteNotification(id)
        }
    }
}
