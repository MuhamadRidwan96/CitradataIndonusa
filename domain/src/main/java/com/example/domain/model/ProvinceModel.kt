package com.example.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class ProvinceModel(
    val idProvince: String? = "",
    val name: String = ""
)

@Immutable
data class StatisticProvince(
    val province : String,
    val total : Int
)