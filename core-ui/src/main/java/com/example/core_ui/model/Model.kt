package com.example.core_ui.model

import androidx.compose.ui.graphics.vector.ImageVector

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
