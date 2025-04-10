package com.example.feature_authentication.state

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isUserNameWrong: Boolean = false,
    val isPassWordWrong: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
