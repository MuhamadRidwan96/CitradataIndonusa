package com.example.features.presentation.favorite.state

import com.example.core_ui.architecture.base.BaseUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class FavoriteUiState(
    val favorite : ImmutableList<FavoriteProjectUiState> = persistentListOf(),
    val isLoading: Boolean = false

) : BaseUiState
