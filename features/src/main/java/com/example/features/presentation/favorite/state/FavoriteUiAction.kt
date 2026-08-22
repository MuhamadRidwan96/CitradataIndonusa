package com.example.features.presentation.favorite.state

import com.example.core_ui.architecture.base.BaseUiAction

interface FavoriteUiAction : BaseUiAction {

    data class RemoveFavorite(
        val projectId: Int = 0
    ) : FavoriteUiAction
}