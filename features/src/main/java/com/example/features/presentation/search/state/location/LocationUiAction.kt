package com.example.features.presentation.search.state.location

import com.example.core_ui.architecture.base.BaseUiAction

interface LocationUiAction : BaseUiAction {

    data object Refresh : LocationUiAction

    data object LoadProvince : LocationUiAction

    data class LoadCity(
        val idProvince : String
    ) : LocationUiAction
}