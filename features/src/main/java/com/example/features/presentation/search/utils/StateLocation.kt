package com.example.features.presentation.search.utils

import androidx.compose.runtime.Immutable

@Immutable
data class ProvinceUiState(
    val idProvince: String = "",
    val province: String = "",
)

@Immutable
data class CityUiState(
    val idCity: String = "",
    val idProvince: String? = "",
    val cityName: String = ""
)