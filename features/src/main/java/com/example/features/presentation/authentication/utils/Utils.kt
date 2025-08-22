package com.example.features.presentation.authentication.utils

import android.util.Patterns
import com.example.common.Result
import com.example.features.presentation.authentication.state.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <T> Flow<Result<T>>.toUiState(): Flow<UiState<T>> = map { result ->
    when (result) {
        is Result.Success -> UiState.Success(result.data)
        is Result.Error -> UiState.Error(result.exception.message ?: "Unknown error")
        is Result.Loading -> UiState.Loading
    }
}

fun isValidEmail(email: String): Boolean {
    return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isValidPassword(password: String): Boolean {
    return password.length >= 6
}