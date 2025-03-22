package com.example.feature_login.presentation.state

interface UiState<out t> {
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val message: String) : UiState<Nothing>
    data object Loading : UiState<Nothing>
    data object Idle : UiState<Nothing>
}