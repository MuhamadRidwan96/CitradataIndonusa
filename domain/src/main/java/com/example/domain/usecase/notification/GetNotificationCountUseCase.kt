package com.example.domain.usecase.notification

import com.example.domain.repository.NotificationRepository
import javax.inject.Inject

class GetNotificationCountUseCase @Inject constructor(
    private val repository: NotificationRepository
) {
    operator fun invoke() = repository.getUnreadCount()
}