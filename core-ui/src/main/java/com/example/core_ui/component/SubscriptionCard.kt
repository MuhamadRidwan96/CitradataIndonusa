package com.example.core_ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core_ui.AppTheme
import com.example.core_ui.R

@Composable
fun SubscriptionCard(
    plan: SubscriptionPlan,
    onSelectPlan: () -> Unit,
    modifier: Modifier = Modifier
) {
    val planStyle = MaterialTheme.typography.headlineMedium.copy(
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.secondary,
    )

    Box(modifier = modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = if (plan.isMostPopular) 12.dp else 0.dp),
            shape = RoundedCornerShape(12.dp),
            border = if (plan.isMostPopular) BorderStroke(
                2.dp,
                MaterialTheme.colorScheme.primary
            ) else null
        ) {
            // Header with plan name and price
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Text(
                    plan.planName,
                    style = planStyle
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
        if (plan.isMostPopular) {
            Text(
                text = "Most Popular",
                color = Color.White,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 16.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}

@Composable
fun BenefitCard(benefit: BenefitPlan) {
    val planStyle = MaterialTheme.typography.titleLarge.copy(
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.secondary,
    )

    val descStyle = MaterialTheme.typography.titleMedium.copy(
        color = Color.Gray
    )
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = benefit.icon,
            contentDescription = null,
            modifier = Modifier
                .size(45.dp, 45.dp)
                .align(Alignment.Top)
        )
        Spacer(modifier = Modifier.size(6.dp))
        Column {
            Text(benefit.planName, style = planStyle)
            Text(benefit.description, style = descStyle)
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

@Composable
private fun PlanDivider() {
    HorizontalDivider(
        thickness = 0.5.dp,
        color = MaterialTheme.colorScheme.outlineVariant
    )
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    AppTheme {
        CompareFeatures()
    }
}

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

data class SubscriptionPlan(
    val planName: String,
    val price: String,
    val description: String,
    val perMonth: String,
    val features: List<Feature>,
    val isEnabled: Boolean,
    val isMostPopular: Boolean = false // default false
)

data class BenefitPlan(
    val planName: String,
    val description: String,
    val icon: ImageVector
)

data class Feature(
    val name: String,
    val included: Boolean

)

data class CompareFeature(
    val name: String,
    val availableIn: List<String>
)

