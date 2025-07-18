package com.example.features.presentation.home.state

import androidx.compose.runtime.Immutable

@Immutable
data class DataState(
    val checkbox: String = "",
    val no: Int = 0,
    val lastUpdate: String = "",
    val idRecord: String = "",
    val idProject: String = "",
    val project: String = "",
    val statProject: String = "",
    val category: String = "",
    val status: String = "",
    val location: String = "",
    val province: String = "",
    val isLoading: Boolean = false,
    val isFavorite: Boolean = false

)