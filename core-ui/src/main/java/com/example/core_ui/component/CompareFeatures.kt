package com.example.core_ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Verified
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.core_ui.model.CompareFeature
import kotlinx.collections.immutable.ImmutableList

@Composable
fun CompareFeatures(
    modifier: Modifier = Modifier,
    features: ImmutableList<CompareFeature>) {
    val planNames = listOf("Free", "Basic", "Premium")

    val planStyle = MaterialTheme.typography.bodyLarge.copy(
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.primary
    )

    Column(modifier = modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Features", style = planStyle, modifier = Modifier.weight(1.5f))
            planNames.forEach {
                Text(
                    it,
                    style = planStyle,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }
        PlanDivider()

        features.forEach { compareFeature ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Text(
                    compareFeature.name,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1.5f)
                )
                planNames.forEach { plan ->
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        if (plan in compareFeature.availableIn) {
                            Icon(
                                imageVector = Icons.Outlined.Verified, // Atau icon centang lain
                                contentDescription = null
                            )
                        }
                    }
                }
            }
            PlanDivider()
        }
    }
}


@Composable
private fun PlanDivider() {
    HorizontalDivider(
        thickness = 0.5.dp,
        color = MaterialTheme.colorScheme.outlineVariant
    )
}
