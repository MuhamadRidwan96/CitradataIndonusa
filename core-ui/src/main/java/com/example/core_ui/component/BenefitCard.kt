package com.example.core_ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.core_ui.model.BenefitPlan

@Composable
fun BenefitCard(benefit: BenefitPlan) {
    val planStyle = MaterialTheme.typography.titleLarge.copy(
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface,
    )

    val descStyle = MaterialTheme.typography.titleMedium.copy(
        color = Color.Gray
    )
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = benefit.icon,
            contentDescription = null,
            modifier = Modifier
                .size(26.dp, 26.dp)
                .align(Alignment.Top),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.size(8.dp))
        Column {
            Text(benefit.planName, style = planStyle)
            Text(benefit.description, style = descStyle)
        }
    }
}