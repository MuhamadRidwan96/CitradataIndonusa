package com.example.core_ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.core_ui.R
import com.example.core_ui.model.Feature
import com.example.core_ui.model.SubscriptionPlan

@Composable
fun SubscriptionCard(
    plan: SubscriptionPlan,
    onSelectPlan: () -> Unit,
    modifier: Modifier = Modifier
) {
    val planStyle = MaterialTheme.typography.headlineMedium.copy(
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface,
    )

    Box(modifier = modifier.fillMaxWidth()) {
        Card(
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onSecondary),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(2.dp, RoundedCornerShape(12.dp))
                .padding(top = if (plan.isMostPopular) 12.dp else 0.dp),
            shape = RoundedCornerShape(12.dp),
            border = if (plan.isMostPopular) BorderStroke(
                2.dp,
                MaterialTheme.colorScheme.primary
            ) else null
        )

        {
            // Header with plan name and price
            Column(
                modifier = Modifier.padding(16.dp),
            ) {

                Text(
                    plan.planName,
                    style = planStyle
                )
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    "${plan.price}${plan.perMonth}",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold
                )

                Text(plan.description, style = MaterialTheme.typography.bodyLarge)

                Spacer(modifier = Modifier.height(12.dp))
                // Features list
                Column(
                    modifier = Modifier.padding(vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    plan.features.forEach { feature ->
                        FeatureItem(feature = feature)
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))

                // Select button
                Button(
                    onClick = onSelectPlan,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    enabled = plan.isEnabled,
                    elevation = null,
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
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
        if (plan.isMostPopular) {
            Text(
                text = "Most Popular",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 16.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}


@Composable
private fun FeatureItem(feature: Feature) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = if (feature.included) Icons.Outlined.Verified else Icons.Outlined.Info,
            contentDescription = if (feature.included) "Included" else "Not included",
            tint = if (feature.included) MaterialTheme.colorScheme.primary else Color.Red
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(feature.name)
    }
}



