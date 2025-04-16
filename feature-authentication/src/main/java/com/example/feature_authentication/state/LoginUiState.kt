package com.example.feature_authentication.state

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isEmailWrong: Boolean = false,
    val isPassWordWrong: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
