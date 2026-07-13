package com.example.features.presentation.home.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class CardConfiguration(
    val topPadding: Dp,
    val border: BorderStroke?,
    val statusColor: Color?
)

fun cardConfiguration(
    status: String,
    colors: ColorScheme
): CardConfiguration {

    val statusColor = when (status) {
        "New" -> colors.surfaceContainerHigh
        "Update" -> colors.surfaceContainerHighest
        else -> null
    }

    return CardConfiguration(
        topPadding = if (statusColor != null) 12.dp else 0.dp,
        border = statusColor?.let { BorderStroke(2.dp, it) },
        statusColor = statusColor
    )
}