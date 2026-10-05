package com.example.features.presentation.authentication.state.login

import com.example.core_ui.architecture.base.BaseUiAction

interface LoginUiAction : BaseUiAction {
    data class EmailChanged(
        val email: String
    ) : LoginUiAction

    data class PasswordChanged(
        val password: String
    ) : LoginUiAction

    data object Login : LoginUiAction

    data object SignInWithGoogle : LoginUiAction

    data object SignUp : LoginUiAction

}