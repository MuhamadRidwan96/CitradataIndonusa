package com.example.core_ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.core_ui.model.BenefitPlan

@Composable
fun BenefitCard(benefit: BenefitPlan) {
    val planStyle = MaterialTheme.typography.titleMedium.copy(
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSurface,
    )

    val descStyle = MaterialTheme.typography.bodyMedium.copy(
        color = Color.Gray
    )
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {

        Icon(
            imageVector = benefit.icon,
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .padding(top = 2.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Column {
            Text(benefit.planName, style = planStyle)
            Text(benefit.description, style = descStyle)
        }
    }
}

