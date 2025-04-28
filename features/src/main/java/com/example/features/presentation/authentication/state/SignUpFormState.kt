package com.example.features.presentation.authentication.state

data class SignUpFormState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val isUsernameWrong: Boolean = false,
    val isEmailWrong: Boolean = false,
    val isPasswordWrong: Boolean = false
)
