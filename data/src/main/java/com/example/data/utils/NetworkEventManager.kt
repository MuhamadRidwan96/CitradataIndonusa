package com.example.data.utils

import com.example.data.network.NetworkEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkEventManager @Inject constructor() {

    private val _events = MutableStateFlow<NetworkEvent?>(null)

    val events: StateFlow<NetworkEvent?> =
        _events.asStateFlow()

    fun emit(event: NetworkEvent) {
        _events.value = event
    }

    fun clearEvents() {
        _events.value = null
    }
}