package com.example.features.utils

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetryCoordinator @Inject constructor() {

    private val _retryEvents = MutableSharedFlow<String>(
        extraBufferCapacity = 1
    )

    val retryEvents =
        _retryEvents.asSharedFlow()

    fun requestRetry(key: String) {
        _retryEvents.tryEmit(key)
    }
}