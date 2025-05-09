package com.example.core_ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.core_ui.R

@Composable
fun SubscriptionCard(
    plan: SubscriptionPlan,
    onSelectPlan: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {

        // Header with plan name and price
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                plan.planName,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                "${plan.price}${plan.perMonth}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )

            Text(plan.description, style = MaterialTheme.typography.bodyMedium)

            // Features list
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                plan.features.forEach { feature ->
                    FeatureItem(feature)
                }
            }
            // Select button
            Button(
                onClick = onSelectPlan,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                enabled = plan.isEnabled,
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 4.dp,
                    pressedElevation = 8.dp,
                    disabledElevation = 0.dp //elevation on disable
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                ),
                shape = RoundedCornerShape(8.dp) // Button shape
            ) {
                Text(
                    text = stringResource(R.string.choose_plan),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}


@Composable
private fun FeatureItem(feature: Feature) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = if (feature.included) Icons.Default.Verified else Icons.Default.ErrorOutline,
            contentDescription = if (feature.included) "Included" else "Not included",
            tint = if (feature.included) MaterialTheme.colorScheme.primary else Color.Red
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(feature.name)
    }
}

data class SubscriptionPlan(
    val planName: String,
    val price: String,
    val description: String,
    val perMonth: String,
    val features: List<Feature>,
    val isEnabled: Boolean
)

data class Feature(
    val name: String,
    val included: Boolean
)
