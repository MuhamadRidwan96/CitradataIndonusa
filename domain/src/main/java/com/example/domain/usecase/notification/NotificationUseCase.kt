package com.example.domain.usecase.notification

import com.example.domain.model.NotificationModel
import com.example.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveNotificationUseCase @Inject constructor(private val repository: NotificationRepository) {
    operator fun invoke(): Flow<List<NotificationModel>> {
        return repository.observeNotifications()
    }

}

class DeleteNotificationUseCase @Inject constructor(private val repository: NotificationRepository) {
    suspend operator fun invoke(id: Int, userId: String): Result<Unit> {
        return repository.delete(id, userId)

    }
}

class MarkNotificationReadUseCase @Inject constructor(
    private val repository: NotificationRepository
) {
    suspend operator fun invoke(id: Int, userId: String): Result<Unit> {
        return repository.markAsRead(id, userId)
    }
}

class SyncNotificationUseCase @Inject constructor(private val repository: NotificationRepository) {
    suspend operator fun invoke(userId: String): Result<Unit> {
        return repository.sync(userId)

    }
}

class ObserveUnreadCountUseCase @Inject constructor(private val repository: NotificationRepository) {
    operator fun invoke(): Flow<Int> {
        return repository.observeUnreadCount()
    }
}
