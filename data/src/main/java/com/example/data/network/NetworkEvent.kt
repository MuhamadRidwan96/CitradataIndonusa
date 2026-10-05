package com.example.data.network

sealed interface NetworkEvent {

    data object TokenExpired : NetworkEvent

    data object NoInternet : NetworkEvent
}