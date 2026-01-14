package com.example.domain.usecase.notification

import com.example.domain.di.IoDispatcher
import com.example.domain.repository.NotificationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetNotificationCountUseCase @Inject constructor(
    private val repository: NotificationRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): Int =
        withContext(dispatcher) { repository.getUnreadCount() }
}