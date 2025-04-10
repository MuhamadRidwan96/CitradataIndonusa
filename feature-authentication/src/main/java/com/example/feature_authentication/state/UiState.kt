package com.example.feature_authentication.state

interface UiState<out t> {
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val message: String) : UiState<Nothing>
    data object Idle : UiState<Nothing>
}