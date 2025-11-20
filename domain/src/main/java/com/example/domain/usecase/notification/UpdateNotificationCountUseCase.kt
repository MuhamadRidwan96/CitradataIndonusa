package com.example.domain.usecase.notification

import com.example.domain.model.NotificationModel
import com.example.domain.repository.NotificationRepository
import javax.inject.Inject

class UpdateNotificationCountUseCase @Inject constructor(
    private val repository: NotificationRepository) {

    suspend operator fun invoke(notification: NotificationModel) {
        repository.insert(notification)
    }
}