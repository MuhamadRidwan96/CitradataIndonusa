package com.example.features.presentation.favorite.state

import com.example.core_ui.architecture.base.BaseUiEvent

interface FavoriteUiEvent : BaseUiEvent{
    data class ShowSnackBar(val message : String): FavoriteUiEvent
}