package com.example.domain.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector

@Immutable
data class SubscriptionPlan(
    val planName: String,
    val price: String,
    val description: String,
    val perMonth: String,
    val features: List<Feature>,
    val isEnabled: Boolean,
    val isMostPopular: Boolean = false // default false
)
@Immutable
data class BenefitPlan(
    val planName: String,
    val description: String,
    val icon: ImageVector
)
@Immutable
data class Feature(
    val name: String,
    val included: Boolean

)
@Immutable
data class CompareFeature(
    val name: String,
    val availableIn: List<String>
)
