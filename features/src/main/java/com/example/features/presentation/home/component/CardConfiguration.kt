package com.example.features.presentation.home.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class CardConfiguration(
    val topPadding: Dp,
    val border: BorderStroke?,
    val statusColor: Color?
)

@Composable
fun rememberCardConfiguration(statProject: String): CardConfiguration {
    val status = cleanStatus(statProject)
    return remember(status) {
        when (status) {
            "New" -> CardConfiguration(
                topPadding = 12.dp,
                border = BorderStroke(
                    2.dp,
                    Color.Unspecified
                ),
                statusColor = Color.Unspecified
            )

            "Update" -> CardConfiguration(
                topPadding = 12.dp,
                border = BorderStroke(2.dp, Color.Unspecified),
                statusColor = Color.Unspecified
            )

            else -> CardConfiguration(
                topPadding = 0.dp,
                border = null,
                statusColor = null
            )
        }
    }.let { config ->
        // Resolve colors at composition time
        val colors = MaterialTheme.colorScheme
        config.copy(
            border = config.border?.copy(
                brush = SolidColor(
                    when (status) {
                        "New" -> colors.surfaceContainerHigh
                        "Update" -> colors.surfaceContainerHighest
                        else -> Color.Transparent
                    }
                )
            ),
            statusColor = when (status) {
                "New" -> colors.surfaceContainerHigh
                "Update" -> colors.surfaceContainerHighest
                else -> config.statusColor
            }
        )
    }
}