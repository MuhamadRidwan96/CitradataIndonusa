package com.example.features.presentation.authentication.state.login

import com.example.core_ui.architecture.base.BaseUiEvent

interface LoginUiEvent : BaseUiEvent {
    data object Success : LoginUiEvent
    data class ShowSnackBar(val message: String) : LoginUiEvent
}