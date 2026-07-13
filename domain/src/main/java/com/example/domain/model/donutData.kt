package com.example.domain.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class DonutData(
    val label: String,
    val value: Float,
    val color: Color
)
