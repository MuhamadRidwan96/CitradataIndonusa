package com.example.features.presentation.detail.state

import com.example.core_ui.architecture.base.BaseUiAction

interface DetailUiAction : BaseUiAction {

    data class LoadDetail(
        val projectId: String
    ) : DetailUiAction

    data class Retry(
        val projectId: String
    ) : DetailUiAction

    /*data object ToggleFavorite : DetailUiAction

    data object ShareProject : DetailUiAction

    data object ShowContact : DetailUiAction

    data object HideContact : DetailUiAction*/
}