package com.example.features.presentation.authentication.state.signup

import com.example.core_ui.architecture.base.BaseUiEvent

interface SignUpUiEvent : BaseUiEvent {
    data object Success : SignUpUiEvent
    data class ShowSnackBar(val message: String) : SignUpUiEvent
}