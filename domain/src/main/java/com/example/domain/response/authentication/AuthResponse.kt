package com.example.domain.response.authentication

sealed interface AuthResponse {
    data object Success:AuthResponse
    data class Error(val message:String):AuthResponse
}