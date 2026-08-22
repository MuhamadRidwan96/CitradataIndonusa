package com.example.features.presentation.search.state.search

import androidx.compose.runtime.Immutable

@Immutable
data class ProjectUiItem(
    val checkbox: String? = "",
    val no: Int? = 0,
    val lastUpdate: String? = "",
    val idRecord: String? = "",
    val idProject: Int? = 0,
    val project: String? = "",
    val statProject: String? = "",
    val category: String?= "",
    val status: String? = "",
    val location: String? = "",
    val province: String? = "",
    val isLoading: Boolean = false,
    val isFavorite: Boolean = false,
    val filters: Map<String, String> = emptyMap()

)