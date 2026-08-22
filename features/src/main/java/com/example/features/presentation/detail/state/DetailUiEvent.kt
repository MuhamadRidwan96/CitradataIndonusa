package com.example.features.presentation.detail.state

import com.example.core_ui.architecture.base.BaseUiEvent

interface DetailUiEvent : BaseUiEvent {

    data class ShowSnackBar(
        val message: String
    ) : DetailUiEvent

}