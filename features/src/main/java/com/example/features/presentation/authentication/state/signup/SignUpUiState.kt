package com.example.features.presentation.authentication.state.signup

import androidx.compose.runtime.Stable
import com.example.core_ui.architecture.base.BaseUiState

@Stable
data class SignUpUiState(
    val username: String = "",
    val email: String = "",
    val password: String = "",

    val isUsernameWrong: Boolean = false,
    val isEmailWrong: Boolean = false,
    val isPasswordWrong: Boolean = false,

    val errorMessage: String? = null,
) : BaseUiState
