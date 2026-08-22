package com.example.features.presentation.authentication.state.signup

import com.example.core_ui.architecture.base.BaseUiAction

interface SignUpUiAction : BaseUiAction {
    data class UsernameChanged(
        val username: String
    ) : SignUpUiAction

    data class EmailChanged(
        val email: String
    ) : SignUpUiAction

    data class PasswordChanged(
        val password: String
    ) : SignUpUiAction

    data object SignUp : SignUpUiAction
}