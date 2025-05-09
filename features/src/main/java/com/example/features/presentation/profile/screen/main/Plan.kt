package com.example.features.presentation.profile.screen.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.core_ui.component.Feature
import com.example.core_ui.component.SubscriptionPlan

@Composable
fun rememberSubscriptionPlans(): List<SubscriptionPlan> = remember {
    listOf(
        SubscriptionPlan(
            planName = "Free",
            price = "$0",
            description = "Basic access with limitation",
            perMonth = SubscriptionConstants.PER_MONTH,
            features = listOf(
                Feature("Limited content access", true),
                Feature("Basic features", true),
                Feature("Ad-supported experience", true)
            ),
            isEnabled = false
        ),
        SubscriptionPlan(
            planName = "Basic",
            price = "$4.99",
            description = "All essentials feature for casual users",
            perMonth = SubscriptionConstants.PER_MONTH,
            features = listOf(
                Feature("Full app access", true),
                Feature("Ad-free experience", true),
                Feature("Download content", true),
                Feature("Standard support", true)
            ),
            isEnabled = true
        ),
        SubscriptionPlan(
            planName = "Premium",
            price = "$9.99",
            description = "Complete access with premium benefit",
            perMonth = SubscriptionConstants.PER_MONTH,
            features = listOf(
                Feature("Everything in basic", true),
                Feature("Exclusive premium content", true),
                Feature("Priority customer support", true),
                Feature("Early access to new feature", true),
                Feature("Member only discount", true)
            ),
            isEnabled = true
        )
    )
}

object SubscriptionConstants {
    const val PER_MONTH = "/month"
}