package com.example.features.presentation.favorite.state

import androidx.compose.runtime.Immutable

@Immutable
data class FavoriteProjectUiState(
    val idProject: Int = 0,
    val lastUpdate: String = "",
    val idRecord: String,
    val project: String,
    val statProject: String,
    val category: String,
    val status: String,
    val location: String,
    val province: String
)
