package com.example.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class TrendProject(
    val month: String,
    val total: Int
)
