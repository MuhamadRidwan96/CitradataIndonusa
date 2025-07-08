package com.example.features.presentation.profile.screen.subscreen.membership

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Discount
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.core_ui.component.BenefitPlan
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
                Feature("Ad-supported experience", true),
                Feature(SubscriptionConstants.EARLY_ACCESS, false),
                Feature(SubscriptionConstants.DISCOUNT, false)
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
                Feature("Standard support", true),
                Feature(SubscriptionConstants.EARLY_ACCESS, false),
                Feature(SubscriptionConstants.DISCOUNT, false)
            ),
            isEnabled = true,
            isMostPopular = true
        ),
        SubscriptionPlan(
            planName = "Premium Content",
            price = "$9.99",
            description = "Complete access with premium benefit",
            perMonth = SubscriptionConstants.PER_MONTH,
            features = listOf(
                Feature("Everything in basic", true),
                Feature("Exclusive premium content", true),
                Feature("Priority customer support", true),
                Feature(SubscriptionConstants.EARLY_ACCESS, true),
                Feature(SubscriptionConstants.DISCOUNT, true)
            ),
            isEnabled = true
        )
    )
}

object SubscriptionConstants {
    const val PER_MONTH = "/month"
    const val EARLY_ACCESS = "Early access to new feature"
    const val DISCOUNT = "Member only discount"
}


@Composable
fun rememberBenefitPlans():List<BenefitPlan> = remember{
    listOf(
        BenefitPlan(
            planName = "Premium Content",
            description = "Access exclusive content not available to free users",
            icon = Icons.Default.Verified
        ),
        BenefitPlan(
            planName = "Priority Support",
            description = "Get faster responses to your questions and issues",
            icon = Icons.Default.WorkspacePremium
        ),
        BenefitPlan(
            planName = "Early Access",
            description = "Be the first to try new features before they're released",
            icon = Icons.Default.CalendarMonth
        ),
        BenefitPlan(
            planName = "Special Discounts",
            description ="Enjoy member-only discounts on in-app purchases",
            icon = Icons.Default.Discount
        )
    )
}

