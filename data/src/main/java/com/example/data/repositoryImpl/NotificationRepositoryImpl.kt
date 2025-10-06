package com.example.data.repositoryImpl

import com.example.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor() : NotificationRepository {

    private val _count = MutableStateFlow(0)
    override val notificationCount: StateFlow<Int>
        get() = _count

    override fun increaseCount() {
        _count.value = _count.value + 1
    }

    override fun resetCount() {
        _count.value = 0
    }
}