package com.example.features.presentation.search.state.location

import com.example.core_ui.architecture.base.BaseUiEvent

interface LocationUiEvent : BaseUiEvent {

    data class ShowSnackBar(
        val message: String
    ) : LocationUiEvent

}