package com.example.domain.response

sealed interface AuthResponse {
    data object Success:AuthResponse
    data class Error(val message:String):AuthResponse
}