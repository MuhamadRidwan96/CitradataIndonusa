package com.example.core_ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.core_ui.model.CompareFeature

@Composable
fun CompareFeatures() {
    val planNames = listOf("Free", "Basic", "Premium")
    val features = rememberComparePlans()

    val planStyle = MaterialTheme.typography.titleMedium.copy(
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.secondary
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Features", style = planStyle, modifier = Modifier.weight(1.5f))
            planNames.forEach {
                Text(it, style = planStyle, modifier = Modifier.weight(1f))
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
                Text(compareFeature.name, style = planStyle, modifier = Modifier.weight(1.5f))
                planNames.forEach { plan ->
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        if (plan in compareFeature.availableIn) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle, // Atau icon centang lain
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
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
fun rememberComparePlans(): List<CompareFeature> = remember {
    listOf(
        CompareFeature("Ad-free experience", listOf("Basic", "Premium")),
        CompareFeature("Unlimited access", listOf("Premium")),
        CompareFeature("Download content", listOf("Basic", "Premium")),
        CompareFeature("Priority support", listOf("Premium")),
        CompareFeature("Exclusive content", listOf("Premium"))
    )
}


@Composable
private fun PlanDivider() {
    HorizontalDivider(
        thickness = 0.5.dp,
        color = MaterialTheme.colorScheme.outlineVariant
    )
}