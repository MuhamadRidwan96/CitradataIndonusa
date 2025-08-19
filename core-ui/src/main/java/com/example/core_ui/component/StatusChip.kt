package com.example.core_ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Immutable
private data class StatusChipColors(
    val backgroundColor: Color,
    val textColor: Color
)

@Composable
fun StatusChip(status: String) {
    val chipColors = remember(status) {
        when (status.lowercase()) {
            "planning" -> StatusChipColors(Color(0xFF4CAF50), Color.White)
            "post tender", "pilling work","piling work" -> StatusChipColors(Color(0xFFFFEB3B), Color.Black)
            "construction start", "project canceled" -> StatusChipColors(
                Color(0xFFF44336),
                Color.White
            )

            "under construction", "final project" -> StatusChipColors(
                Color(0xFF2196F3),
                Color.White
            )

            "existing", "hold project" -> StatusChipColors(Color(0xFFFF9800), Color.Black)
            "finish" -> StatusChipColors(Color.Gray, Color.White)
            else -> StatusChipColors(Color.Transparent, Color.Black)
        }
    }

    Text(
        text = status,
        color = chipColors.textColor,
        modifier = Modifier
            .background(
                color = chipColors.backgroundColor,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp),
        style = MaterialTheme.typography.bodySmall,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}