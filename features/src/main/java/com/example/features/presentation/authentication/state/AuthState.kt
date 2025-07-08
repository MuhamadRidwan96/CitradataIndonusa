package com.example.features.presentation.authentication.state

import androidx.compose.runtime.Immutable

@Immutable
data class LoginFormState(
    val email: String = "",
    val password: String = "",
    val isEmailWrong: Boolean = false,
    val isPassWordWrong: Boolean = false,
    val errorMessage: String? = null,
)

@Immutable
data class LoginProcessState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val isReady: Boolean = false
)

@Immutable
data class SignUpFormState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val isUsernameWrong: Boolean = false,
    val isEmailWrong: Boolean = false,
    val isPasswordWrong: Boolean = false,
    val errorMessage: String? = null,
)