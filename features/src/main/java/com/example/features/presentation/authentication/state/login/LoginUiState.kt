package com.example.features.presentation.authentication.state.login

import androidx.compose.runtime.Stable
import com.example.core_ui.architecture.base.BaseUiState


@Stable
data class LoginUiState(


    val email: String = "",
    val password: String = "",

    val isEmailWrong: Boolean = false,
    val isPassWordWrong: Boolean = false,

    val errorMessage: String? = null,

    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val isReady: Boolean = false
): BaseUiState