package com.example.features.presentation.profile.screen.state

import com.example.core_ui.architecture.base.BaseUiEvent

interface ProfileUiEvent: BaseUiEvent {

    data object LogoutSuccess : ProfileUiEvent

    data class ShowSnackBar(
        val message: String
    ) : ProfileUiEvent
}